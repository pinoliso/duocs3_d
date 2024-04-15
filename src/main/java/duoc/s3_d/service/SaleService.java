package duoc.s3_d.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import duoc.s3_d.repository.SaleRepository;
import duoc.s3_d.model.Sale;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    public void saveSale(Sale sale) {
        saleRepository.save(sale);
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

}
