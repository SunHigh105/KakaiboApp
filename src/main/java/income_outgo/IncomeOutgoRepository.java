package income_outgo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IncomeOutgoRepository extends JpaRepository<IncomeOutgo, Long> {
    @Query("SELECT i FROM IncomeOutgo i WHERE i.date >= :startDate AND i.date < :lastDate")
    List<IncomeOutgo> findByMonth(@Param("startDate") Date startDate, @Param("lastDate") Date lastDate);
    @Query("SELECT i FROM IncomeOutgo i WHERE i.date >= :startYear AND i.date < :lastYear")
    List<IncomeOutgo> findByYear(@Param("startYear") Date startYear, @Param("lastYear") Date lastYear);
    @Query("SELECT SUM(i.cost) FROM IncomeOutgo i WHERE i.type = :type AND (i.date >= :startDate AND i.date < :lastDate)")
    Integer costTotal(@Param("type") String type, @Param("startDate") Date startDate, @Param("lastDate") Date lastDate);
}