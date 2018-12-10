package income_outgo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //
public class IncomeOutgoService {
    @Autowired
    private IncomeOutgoRepository incomeOutgoRepository;

    public List<IncomeOutgo> findAll() {
        return incomeOutgoRepository.findAll();
    }

//    public List<IncomeOutgo> findByMonth(String month){
//        return incomeOutgoRepository.findByMonth(month);
//    }

    public IncomeOutgo findById(Long id){
//        return incomeOutgoRepository.findById(id).orElse(null);
        return incomeOutgoRepository.findById(id).orElse(null);
    }
//    public List<IncomeOutgo> findByMonth(Date start, Date end){
//        return incomeOutgoRepository.findByStartDateBetween(start, end);
//    }
//
    public IncomeOutgo save(IncomeOutgo incomeOutgo){
        return incomeOutgoRepository.save(incomeOutgo);
    }
//
    public void deleteById(Long id){
        incomeOutgoRepository.deleteById(id);
    }
}
