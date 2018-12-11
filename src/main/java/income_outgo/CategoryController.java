package income_outgo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // 今月（yyyy-MM）の文字列
    Date today = new Date();
    SimpleDateFormat thisMonthFormat = new SimpleDateFormat("yyyy-MM");
    String thisMonthPath = thisMonthFormat.format(today);

    @GetMapping("setting")
    public String newcCategory(Model model){
        List<Category> categories_outgo = categoryService.findByType("outgo");
        model.addAttribute("categories_outgo", categories_outgo);
        List<Category> categories_income = categoryService.findByType("income");
        model.addAttribute("categories_income", categories_income);
        //今月をリンクに入れる
        model.addAttribute("thisMonthPath", thisMonthPath);
        return "category/setting";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        //今月をリンクに入れる
        model.addAttribute("thisMonthPath", thisMonthPath);
        return "category/edit";
    }


    @PostMapping("/setting")
    public String create(@ModelAttribute Category category){
        categoryService.save(category);
        return "redirect:/category/setting";
    }

    @PutMapping("{id}")
    public String update(@PathVariable Long id, @ModelAttribute Category category){
        category.setId(id);
        categoryService.save(category);
        return "redirect:/category/setting";
    }

    @DeleteMapping("{id}")
    public String destroy(@PathVariable Long id){
        categoryService.deleteById(id);
        return "redirect:/category/setting";
    }
}

