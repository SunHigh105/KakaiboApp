package income_outgo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findByType(String type);
//    Category findOne(Integer id);
//    void delete(Integer id);
}
