package income_outgo;

import org.springframework.data.jpa.repository.JpaRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called incomeOutgoRepository
// CRUD refers Create, Read, Update, Delete

public interface IncomeOutgoRepository extends JpaRepository<IncomeOutgo, Integer> {
//    IncomeOutgo findOne(Integer id);
//    void delete(Integer id);
}