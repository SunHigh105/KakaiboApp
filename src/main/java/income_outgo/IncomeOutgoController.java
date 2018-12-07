package income_outgo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
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