package income_outgo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    private IncomeOutgoService incomeOutgoService;

    // 基準となる日
    private Date today(){
        Date today = new Date();
        return today;
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

    @GetMapping("setting")
    public String newCategory(Category category, Model model){
        model.addAttribute("categories_outgo", categories("outgo"));
        model.addAttribute("categories_income", categories("income"));
        //今月をリンクに入れる
        model.addAttribute("thisMonthPath", thisMonthPath());
        //今年をリンクに入れる
        model.addAttribute("thisYearPath", thisYearPath());
        return "category/setting";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        //今月をリンクに入れる
        model.addAttribute("thisMonthPath", thisMonthPath());
        //今年をリンクに入れる
        model.addAttribute("thisYearPath", thisYearPath());
        return "category/edit";
    }

    @PostMapping("/setting")
    public String create(@Validated @ModelAttribute Category category,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes resistInfo){
        if(bindingResult.hasErrors()){
            return newCategory(category, model);
        }else{
            categoryService.save(category);
            resistInfo.addFlashAttribute("flash", "データを登録しました！");
            return "redirect:/category/setting";
        }
    }

    @PutMapping("{id}")
    public String update(@PathVariable Long id,
                         @Validated @ModelAttribute Category category,
                         BindingResult bindingResult,
                         RedirectAttributes updateInfo){
        if(bindingResult.hasErrors()){
            return "category/edit";
        }else{
            category.setId(id);
            categoryService.save(category);
            updateInfo.addFlashAttribute("flash", "データを更新しました！");
            return "redirect:/category/setting";
        }
    }

    @DeleteMapping("{id}")
    public String destroy(@PathVariable Long id, RedirectAttributes deleteInfo){
        categoryService.deleteById(id);
        deleteInfo.addFlashAttribute("flash", "データを削除しました！");
        return "redirect:/category/setting";
    }
}

