package income_outgo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class IncomeOutgoController {
    @Autowired
    private IncomeOutgoService incomeOutgoService;

    @GetMapping("new")
    public String newIncomeOutgo(Model model){
        return "new";
    }

    @GetMapping("month")
    public String month(Model model){
        Date today = new Date();
//      // 見出しの年月表示
        SimpleDateFormat thisMonthFormat = new SimpleDateFormat("yyyy年MM月");
        String thisMonth = thisMonthFormat.format(today);
        model.addAttribute("thisMonth", thisMonth);

        // URLの年月表示
        SimpleDateFormat thisMonthPathFormat = new SimpleDateFormat("yyyy-MM");
        String thisMonthPath = thisMonthPathFormat.format(today);

        // 月別一覧表示
        List<IncomeOutgo> monthList = incomeOutgoService.findAll();
        model.addAttribute("monthList", monthList);
        return "month";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        Optional<IncomeOutgo> incomeOutgo = incomeOutgoService.findById(id);
        model.addAttribute("incomeOutgo", incomeOutgo);
        return "edit";
    }

    @PostMapping("new")
    public String create(@ModelAttribute IncomeOutgo incomeOutgo){
        incomeOutgoService.save(incomeOutgo);
        return "redirect:/new";
    }


    @PutMapping("{id}")
    public String update(@PathVariable Long id, @ModelAttribute IncomeOutgo incomeOutgo){
        incomeOutgo.setId(id);
        incomeOutgoService.save(incomeOutgo);
        return "redirect:/month";
    }

    @DeleteMapping("{id}")
    public String destroy(@PathVariable  Long id){
        incomeOutgoService.deleteById(id);
        return "redirect:/month";
    }

}