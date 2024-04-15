package duoc.s3_d.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import duoc.s3_d.model.Sale;
import java.util.List;
import java.time.LocalDate;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByDate(LocalDate saleDate);
    List<Sale> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
