package income_outgo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("new")
    public String newIncomeOutgo(Model model){
        List<Category> categories_outgo = categoryService.findByType("outgo");
        model.addAttribute("categories_outgo", categories_outgo);
        List<Category> categories_income = categoryService.findByType("income");
        model.addAttribute("categories_income", categories_income);
        // 今月（yyyy-MM）の文字列
        Date today = new Date();
        SimpleDateFormat thisMonthPathFormat = new SimpleDateFormat("yyyy-MM");
        String thisMonthPath = thisMonthPathFormat.format(today);
        //今月をリンクに入れる
        model.addAttribute("thisMonthPath", thisMonthPath);
        return "income_outgo/new";
    }

    @GetMapping("month/{thisMonthPath}")
    public String month(@PathVariable String thisMonthPath, Model model) {
        // 見出しの年月表示
        SimpleDateFormat thisMonthFormat = new SimpleDateFormat("yyyy年MM月");
        String thisMonth = thisMonthFormat.format(thisMonthPath);
        model.addAttribute("thisMonth", thisMonth);

        // カテゴリ名表示のため、カテゴリ一覧を取得
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        // 今月の一覧表示
        SimpleDateFormat thisMonthPathFormat = new SimpleDateFormat("yyyy-MM");
        Date today = thisMonthPathFormat.parse(thisMonthPath);

        List<IncomeOutgo> monthList = incomeOutgoService.findByMonth(today);
        model.addAttribute("monthList", monthList);

        // 前月の一覧のパス取得
        Date prevMonth = incomeOutgoService.prevMonth(today);
        String prevMonthPath = thisMonthPathFormat.format(prevMonth);
        model.addAttribute("prevMonthPath", prevMonthPath);

        // 次月の一覧のパス取得
        Date nextMonth = incomeOutgoService.nextMonth(today);
        String nextMonthPath = thisMonthPathFormat.format(nextMonth);
        model.addAttribute("nextMonthPath", nextMonthPath);

        //今月をリンクに入れる
        model.addAttribute("thisMonthPath", thisMonthPath);

        return "income_outgo/month";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        IncomeOutgo incomeOutgo = incomeOutgoService.findById(id);
        model.addAttribute("incomeOutgo", incomeOutgo);
        // 今月（yyyy-MM）の文字列
        Date today = new Date();
        SimpleDateFormat thisMonthPathFormat = new SimpleDateFormat("yyyy-MM");
        String thisMonthPath = thisMonthPathFormat.format(today);
        //今月をリンクに入れる
        model.addAttribute("thisMonthPath", thisMonthPath);
        return "income_outgo/edit";
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