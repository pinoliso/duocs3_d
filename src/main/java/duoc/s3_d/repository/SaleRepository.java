package duoc.s3_d.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import duoc.s3_d.model.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

}
