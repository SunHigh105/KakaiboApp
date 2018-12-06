package income_outgo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class IncomeOutgoController {
    @Autowired
    private IncomeOutgoService incomeOutgoService;

    @GetMapping("new")
    public String newIncomeOutgo(Model model){
        return "/new";
    }

    @GetMapping("month")
    public String month(Model model){
        List<IncomeOutgo> month = incomeOutgoService.findAll();
        model.addAttribute("/", month);
        return "/month";
    }

    @PostMapping("new")
    public String create(@ModelAttribute IncomeOutgo incomeOutgo){
        incomeOutgoService.save(incomeOutgo);
        return "redirect:/new";
    }

    @PutMapping("{id}")
    public String update(@PathVariable Integer id, @ModelAttribute IncomeOutgo incomeOutgo){
        incomeOutgo.setId(id);
        incomeOutgoService.save(incomeOutgo);
        return "redirect:/new";
    }


}