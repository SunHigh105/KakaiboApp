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

    @GetMapping("new")
    public String newIncomeOutgo(Model model){
        List<Category> categories_outgo = categoryService.findByType("outgo");
        model.addAttribute("categories_outgo", categories_outgo);
        List<Category> categories_income = categoryService.findByType("income");
        model.addAttribute("categories_income", categories_income);
        return "income_outgo/new";
    }

    @GetMapping("month")
    public String month(Model model) throws ParseException {
        Date today = new Date();
//      // 見出しの年月表示
        SimpleDateFormat thisMonthFormat = new SimpleDateFormat("yyyy年MM月");
        String thisMonth = thisMonthFormat.format(today);
        model.addAttribute("thisMonth", thisMonth);

        // カテゴリ名表示のため、カテゴリ一覧を取得
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        // URLの年月表示
        SimpleDateFormat thisMonthPathFormat = new SimpleDateFormat("yyyy-MM");
        String thisMonthPath = thisMonthPathFormat.format(today);

        // 月別一覧表示
        List<IncomeOutgo> monthList = incomeOutgoService.findByMonth(today);
        model.addAttribute("monthList", monthList);
        return "income_outgo/month";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        IncomeOutgo incomeOutgo = incomeOutgoService.findById(id);
        model.addAttribute("incomeOutgo", incomeOutgo);
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