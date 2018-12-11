package income_outgo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service //
public class IncomeOutgoService {
    @Autowired
    private IncomeOutgoRepository incomeOutgoRepository;

    public List<IncomeOutgo> findAll() {
        return incomeOutgoRepository.findAll();
    }

    public List<IncomeOutgo> findByMonth(Date today){
        Calendar firstDay = createFirstDay(today);
        Date startDate = firstDay.getTime();
        // 1ヵ月プラスしてからDate型に変換
        firstDay.add(Calendar.MONTH, 1);
        Date lastDate = firstDay.getTime();
        return incomeOutgoRepository.findByMonth(startDate, lastDate);
    }

    public IncomeOutgo findById(Long id){
//        return incomeOutgoRepository.findById(id).orElse(null);
        return incomeOutgoRepository.findById(id).orElse(null);
    }

    public IncomeOutgo save(IncomeOutgo incomeOutgo){
        return incomeOutgoRepository.save(incomeOutgo);
    }
//
    public void deleteById(Long id){
        incomeOutgoRepository.deleteById(id);
    }

    private Calendar createFirstDay(Date date){
        Calendar cal = Calendar.getInstance();
        //引数dateをDate型→カレンダー型に変換
        cal.setTime(date);
        //X月1日に再設定
        cal.set(Calendar.DAY_OF_MONTH, 1);
        //時間・分・秒・ミリ秒は削除
        cal.clear(Calendar.HOUR_OF_DAY);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        return cal;
    }
}
