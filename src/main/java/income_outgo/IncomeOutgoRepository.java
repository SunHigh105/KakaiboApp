package income_outgo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeOutgoRepository extends JpaRepository<IncomeOutgo, Integer> {
//    @Query("select * from income_outgo  where DATE_FORMAT(date,'%Y-%m') = :month")
//    List<IncomeOutgo> findByMonth(@Param("month") String month);

}