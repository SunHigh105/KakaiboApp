package income_outgo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

//    public List<Category> findAll(){
//        return categoryRepository.findAll();
//    }

    public List<Category> findByType(String type){
        return categoryRepository.findByType(type);
    }

//    public Category findOne(Integer id){
//        return categoryRepository.findOne(id);
//    }

    public Category save(Category category){
        return categoryRepository.save(category);
    }

//    public void delete(Integer id){
//        categoryRepository.delete(id);
//    }
}
