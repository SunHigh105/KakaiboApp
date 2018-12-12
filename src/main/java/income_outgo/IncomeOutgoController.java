package income_outgo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

//    // 基準となる日
//    private Date centerMonth(String monthPath) throws ParseException {
//        //年のパターン
//        Pattern yearPattern = Pattern.compile("¥d¥d¥d¥d");
//        Matcher yearMatcher = yearPattern.matcher(monthPath);
//        Integer year = parseInt(yearMatcher.group());
//
//        //月のパターン
//        Pattern monthPattern = Pattern.compile("¥d{2}$");
//        Matcher monthMatcher = monthPattern.matcher(monthPath);
//        Integer month = parseInt(monthMatcher.group());
//
//        //基準日を作る
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.YEAR, year-1);
//        cal.set(Calendar.MONTH, month-1);
//        cal.set(Calendar.DAY_OF_MONTH, 1);
//
//        return cal.getTime();
//    }

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
    public String newIncomeOutgo(Model model){
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
        String thisMonth = thisMonthFormat.format(today());
        model.addAttribute("thisMonth", thisMonth);

        // カテゴリ名表示のため、カテゴリ一覧を取得
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        // 今月の一覧表示
        SimpleDateFormat thisMonthPathFormat = new SimpleDateFormat("yyyy-MM");
        List<IncomeOutgo> monthList = incomeOutgoService.findByMonth(today());
        model.addAttribute("monthList", monthList);

        // 前月の一覧のパス取得
        Date prevMonth = incomeOutgoService.prevMonth(today());
        String prevMonthPath = thisMonthPathFormat.format(prevMonth);
        model.addAttribute("prevMonthPath", prevMonthPath);

        // 次月の一覧のパス取得
        Date nextMonth = incomeOutgoService.nextMonth(today());
        String nextMonthPath = thisMonthPathFormat.format(nextMonth);
        model.addAttribute("nextMonthPath", nextMonthPath);

        //今月をリンクに入れる
        model.addAttribute("thisMonthPath", thisMonthPath());

        //今年(yyyyの文字列)
        model.addAttribute("thisYearPath", thisYearPath());

        return "income_outgo/month";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable Long id, Model model){
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
        Integer yearOutgoTotal = incomeOutgoService.yearOutgoTotal(today());
        model.addAttribute("yearOutgoTotal", yearOutgoTotal);

        //年間の収入合計
        Integer yearIncomeTotal = incomeOutgoService.yearIncomeTotal(today());
        model.addAttribute("yearIncomeTotal", yearIncomeTotal);

        //年間合計
        Integer yearTotal = yearIncomeTotal - yearOutgoTotal;
        model.addAttribute("yearTotal", yearTotal);

        model.addAttribute("thisYearPath", thisYearPath);

        model.addAttribute("thisMonthPath", thisMonthPath());

//        // 年間の一覧表示
//        List<IncomeOutgo>
        return "income_outgo/year";
    }


    @PostMapping("new")
    public String create(@ModelAttribute IncomeOutgo incomeOutgo){
        incomeOutgoService.save(incomeOutgo);
        return "redirect:/income_outgo/new";
    }


    @PutMapping("{id}")
    public String update(@PathVariable Long id, @ModelAttribute IncomeOutgo incomeOutgo){
        incomeOutgo.setId(id);
        incomeOutgoService.save(incomeOutgo);
        return "redirect:/income_outgo/month";
    }

    @DeleteMapping("{id}")
    public String destroy(@PathVariable  Long id){
        incomeOutgoService.deleteById(id);
        return "redirect:/income_outgo/month";
    }

}