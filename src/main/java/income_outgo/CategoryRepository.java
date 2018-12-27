package income_outgo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByType(String type);
    @Query("SELECT COUNT(i) FROM IncomeOutgo i " +
            "INNER JOIN FETCH Category c ON c.id = i.category_id " +
            "WHERE c.id = :id ")
    Integer incomeOutgoCount(@Param("id") Long id);

}
