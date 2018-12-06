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

//    public IncomeOutgo findOne(Integer id){
//        return incomeOutgoRepository.findOne(id);
//    }
//
    public IncomeOutgo save(IncomeOutgo incomeOutgo){
        return incomeOutgoRepository.save(incomeOutgo);
    }
//
//    public void delete(Integer id){
//        incomeOutgoRepository.delete(id);
//    }
}
