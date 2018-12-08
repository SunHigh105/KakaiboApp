package income_outgo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/setting")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String newcCategory(Model model){
        List<Category> categories_outgo = categoryService.findByType("outgo");
        model.addAttribute("categories_outgo", categories_outgo);
        List<Category> categories_income = categoryService.findByType("income");
        model.addAttribute("categories_income", categories_income);
        return "/setting";
    }
//    @GetMapping("/{id}/edit")
//    public String edit(@PathVariable Integer id, Model model){
//
//    }


    @PostMapping
    public String create(@ModelAttribute Category category){
        categoryService.save(category);
        return "redirect:/setting";
    }

    @PutMapping("{id}")
    public String update(@PathVariable Long id, @ModelAttribute Category category){
        category.setId(id);
        categoryService.save(category);
        return "redirect:/setting";
    }

    @DeleteMapping("{id}")
    public String destroy(@PathVariable Long id){
        categoryService.deleteById(id);
        return "redirect:/setting";
    }
}

