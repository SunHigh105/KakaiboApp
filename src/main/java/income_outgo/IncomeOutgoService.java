package income_outgo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class IncomeOutgoService {
    @Autowired
    private IncomeOutgoRepository incomeOutgoRepository;

    public List<IncomeOutgo> findAll() {
        return incomeOutgoRepository.findAll();
    }

    // 一ヶ月の一覧
    public List<IncomeOutgo> findByMonth(Date today){
        Calendar firstDay = createFirstDay(today);
        Date startDate = firstDay.getTime();
        // 1ヵ月プラスしてからDate型に変換
        firstDay.add(Calendar.MONTH, 1);
        Date lastDate = firstDay.getTime();
        return incomeOutgoRepository.findByMonth(startDate, lastDate);
    }

    //前月のX月1日
    public Date prevMonth(Date today){
        Calendar firstDay = createFirstDay(today);
        firstDay.add(Calendar.MONTH, -1);
        Date prevMonth = firstDay.getTime();
        return prevMonth;
    }

    //来月のX月1日
    public Date nextMonth(Date today){
        Calendar firstDay = createFirstDay(today);
        firstDay.add(Calendar.MONTH, 1);
        Date nextMonth = firstDay.getTime();
        return nextMonth;
    }

    //今年の1月1日
    public Date startDateYear(Date today){
        Calendar firstYear = createFirstYear(today);
        return firstYear.getTime();
    }

    //前年の1月1日
    public Date prevYear(Date today){
        Calendar firstDay = createFirstDay(today);
        // 1年マイナスしてからDate型に変換
        firstDay.add(Calendar.YEAR, -1);
        return firstDay.getTime();
    }

    //翌年の1月1日
    public Date nextYear(Date today){
        Calendar firstDay = createFirstDay(today);
        // 1年プラスしてからDate型に変換
        firstDay.add(Calendar.YEAR, 1);
        return firstDay.getTime();
    }

    // 年間の支出合計を取得
    public Integer yearOutgoTotal(Date today){
        return incomeOutgoRepository.costTotal("outgo", startDateYear(today), nextYear(today));
    }
    // 年間の収入合計を取得
    public Integer yearIncomeTotal(Date today){
        return incomeOutgoRepository.costTotal("income", startDateYear(today), nextYear(today));
    }

    // 月間の収入or支出合計をリストで取得
    public Object[][] monthTotal(Date today){
        Calendar startCal = createFirstYear(today);
        Object result[][];
        result = new Object[12][36];

        for (int i = 0; i <= 11; i++){
            Date startDate = startCal.getTime();

            SimpleDateFormat thisMonthFormat = new SimpleDateFormat("yyyy年MM月");
            String dateIndex = thisMonthFormat.format(startDate);
            result[i][0] = dateIndex;

            startCal.add(Calendar.MONTH, 1);
            Date lastDate = startCal.getTime();

            Integer income = incomeOutgoRepository.costTotal("income", startDate, lastDate);
            if (income == null){
                result[i][1] = 0;
            }else{
                result[i][1] = income;
            }

            Integer outgo = incomeOutgoRepository.costTotal("outgo", startDate, lastDate);
            if (outgo == null){
                result[i][2] = 0;
            }else{
                result[i][2] = outgo;
            }

        }
        return result;
    }


    public IncomeOutgo findById(Long id){
        return incomeOutgoRepository.findById(id).orElse(null);
    }

    public IncomeOutgo save(IncomeOutgo incomeOutgo){
        return incomeOutgoRepository.save(incomeOutgo);
    }

    public void deleteById(Long id){
        incomeOutgoRepository.deleteById(id);
    }

    // X月1日を取得
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

    // X年1月1日を取得
    private Calendar createFirstYear(Date date){
        Calendar cal = Calendar.getInstance();
        //引数dateをDate型→カレンダー型に変換
        cal.setTime(date);
        //1月1日に再設定
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        //時間・分・秒・ミリ秒は削除
        cal.clear(Calendar.HOUR_OF_DAY);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        return cal;
    }


}
