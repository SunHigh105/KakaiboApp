package income_outgo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

@Controller
@RequestMapping("/income_outgo")
public class IncomeOutgoController {
    @Autowired
    private IncomeOutgoService incomeOutgoService;
    @Autowired
    private CategoryService categoryService;

    // 今日
    private Date today(){
        Date today = new Date();
        return today;
    }

    // 基準となる日
    private Date centerMonth(String monthPath){
        //年のパターン
        Pattern yearPattern = Pattern.compile("^[0-9]{4}");
        Matcher yearMatcher = yearPattern.matcher(monthPath);
        yearMatcher.find();
        Integer year = parseInt(yearMatcher.group());

        //月のパターン
        Pattern monthPattern = Pattern.compile("[0-9]{2}$");
        Matcher monthMatcher = monthPattern.matcher(monthPath);
        monthMatcher.find();
        Integer month = parseInt(monthMatcher.group());

        //基準日を作る
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        return cal.getTime();
    }
    // 基準となる年
    private Date centerYear(String yearPath){
        //年のパターン
        Pattern yearPattern = Pattern.compile("^[0-9]{4}");
        Matcher yearMatcher = yearPattern.matcher(yearPath);
        yearMatcher.find();
        Integer year = parseInt(yearMatcher.group());

        //基準日を作る
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        return cal.getTime();
    }

    // 今月（yyyy-MM）の文字列
    private String thisMonthPath(){
        SimpleDateFormat thisMonthPathFormat = new SimpleDateFormat("yyyy-MM");
        return thisMonthPathFormat.format(today());
    }

    //今年(yyyyの文字列)
    private String thisYearPath(){
        SimpleDateFormat thisYearPathFormat = new SimpleDateFormat("yyyy");
        return thisYearPathFormat.format(today());
    }

    // カテゴリのリスト
    private List<Category> categories(String type){
        return categoryService.findByType(type);
    }

    @GetMapping("new")
    public String newIncomeOutgo(IncomeOutgo incomeOutgo, Model model){
        // カテゴリを取得
        model.addAttribute("categories_outgo", categories("outgo"));
        model.addAttribute("categories_income", categories("income"));
        //今月をリンクに入れる
        model.addAttribute("thisMonthPath", thisMonthPath());
        // 今年をリンクに入れる
        model.addAttribute("thisYearPath", thisYearPath());
        return "income_outgo/new";
    }

    @GetMapping("month/{thisMonthPath}")
    public String month(@PathVariable String thisMonthPath, Model model) throws ParseException {

        // 見出しの年月表示
        SimpleDateFormat thisMonthFormat = new SimpleDateFormat("yyyy年MM月");
        String thisMonth = thisMonthFormat.format(centerMonth(thisMonthPath));
        model.addAttribute("thisMonth", thisMonth);

        // カテゴリ名表示のため、カテゴリ一覧を取得
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        // 今月の一覧表示
        SimpleDateFormat thisMonthPathFormat = new SimpleDateFormat("yyyy-MM");
        List<IncomeOutgo> monthList = incomeOutgoService.findByMonth(centerMonth(thisMonthPath));
        model.addAttribute("monthList", monthList);

        // 基準の月のパス取得
        String centerMonthPath = thisMonthPathFormat.format(centerMonth(thisMonthPath));
        model.addAttribute("centerMonthPath", centerMonthPath);

        // 前月の一覧のパス取得
        Date prevMonth = incomeOutgoService.prevMonth(centerMonth(thisMonthPath));
        String prevMonthPath = thisMonthPathFormat.format(prevMonth);
        model.addAttribute("prevMonthPath", prevMonthPath);

        // 次月の一覧のパス取得
        Date nextMonth = incomeOutgoService.nextMonth(centerMonth(thisMonthPath));
        String nextMonthPath = thisMonthPathFormat.format(nextMonth);
        model.addAttribute("nextMonthPath", nextMonthPath);

        //今月をリンクに入れる
        model.addAttribute("thisMonthPath", thisMonthPath());

        //今年(yyyyの文字列)
        model.addAttribute("thisYearPath", thisYearPath());

        return "income_outgo/month";
    }

    @GetMapping({"{id}","{id}/edit"})
    public String edit(@PathVariable Long id,
                       Model model){
        IncomeOutgo incomeOutgo = incomeOutgoService.findById(id);
        model.addAttribute("incomeOutgo", incomeOutgo);
        // カテゴリ
        model.addAttribute("categories_outgo", categories("outgo"));
        model.addAttribute("categories_income", categories("income"));

        //今月をリンクに入れる
        model.addAttribute("thisMonthPath", thisMonthPath());

        //今年をリンクに入れる
        model.addAttribute("thisYearPath", thisYearPath());

        return "income_outgo/edit";
    }

    @GetMapping("year/{thisYearPath}")
    public String year(@PathVariable String thisYearPath, Model model){

        //年間の支出合計
        Integer yearOutgoTotal = incomeOutgoService.yearOutgoTotal(centerYear(thisYearPath));
        model.addAttribute("yearOutgoTotal", yearOutgoTotal);

        //年間の収入合計
        Integer yearIncomeTotal = incomeOutgoService.yearIncomeTotal(centerYear(thisYearPath));
        model.addAttribute("yearIncomeTotal", yearIncomeTotal);

        //年間合計
        try{
            Integer yearTotal = yearIncomeTotal - yearOutgoTotal;
            model.addAttribute("yearTotal", yearTotal);

        }catch(NullPointerException e){
            model.addAttribute("yearTotal", 0);
        }
        model.addAttribute("thisYearPath", thisYearPath);

        model.addAttribute("thisMonthPath", thisMonthPath());

        // 月ごとの合計
        model.addAttribute("monthTotal", incomeOutgoService.monthTotal(centerYear(thisYearPath)));

        //前年のパス
        SimpleDateFormat thisYearPathFormat = new SimpleDateFormat("yyyy");
        String prevYear = thisYearPathFormat.format(incomeOutgoService.prevYear(centerYear(thisYearPath)));
        model.addAttribute("prevYear", prevYear);

        //翌年のパス
        String nextYear = thisYearPathFormat.format(incomeOutgoService.nextYear(centerYear(thisYearPath)));
        model.addAttribute("nextYear", nextYear);

        //翌年のパス

        return "income_outgo/year";
    }


    @PostMapping("new")
    public String create(@ModelAttribute @Validated IncomeOutgo incomeOutgo,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes resistInfo){
        if(bindingResult.hasErrors()){
            return newIncomeOutgo(incomeOutgo, model);
//            return "/income_outgo/new";
        }else{
            incomeOutgoService.save(incomeOutgo);
            resistInfo.addFlashAttribute("flash", "データを登録しました！");
            return "redirect:/income_outgo/new";
        }
    }


    @PutMapping("{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute @Validated IncomeOutgo incomeOutgo,
                         BindingResult bindingResult,
                         RedirectAttributes updateInfo){
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.getAllErrors());
            return "income_outgo/edit";
        }else{
            incomeOutgo.setId(id);
            incomeOutgoService.save(incomeOutgo);
            updateInfo.addFlashAttribute("flash", "データを更新しました！");
            return "redirect:/income_outgo/{id}/edit";
        }
    }

    @DeleteMapping("month/{centerMonthPath}/{id}")
    public String destroy(@PathVariable  Long id,
                          @PathVariable String centerMonthPath,
                          RedirectAttributes deleteInfo){
        incomeOutgoService.deleteById(id);
        deleteInfo.addFlashAttribute("flash", "データを削除しました！");
        return "redirect:/income_outgo/month/{centerMonthPath}";
    }

}