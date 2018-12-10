package income_outgo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeOutgoRepository extends JpaRepository<IncomeOutgo, Long> {
    @Query(value = "select * from income_outgo  where DATE_FORMAT(date,'%Y-%m') = :month", nativeQuery=true)
    List<IncomeOutgo> findByMonth(@Param("month") String month);
}