package duoc.s3_d.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.text.DecimalFormat;

import duoc.s3_d.repository.SaleRepository;
import duoc.s3_d.repository.SaleDetailRepository;
import duoc.s3_d.repository.ProductRepository;
import duoc.s3_d.model.Sale;
import duoc.s3_d.model.SaleDetail;
import duoc.s3_d.model.Product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SaleService {

    private static final Logger log = LoggerFactory.getLogger(SaleService.class);

    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private SaleDetailRepository saleDetailRepository;
    @Autowired
    private ProductRepository productRepository;

    public Sale createSale(Sale sale) {
        return saleRepository.save(sale);
    }

    public Optional<Sale> saveSale(Sale sale) {
        Sale newSale = saleRepository.save(sale);
        for(SaleDetail saleDetail: newSale.getSaleDetails()) {
            Optional<Product> optionalProduct = productRepository.findById(saleDetail.getProduct().getId());
            saleDetail.setProduct((Product) optionalProduct.get());
            saleDetail.setSale(newSale);
        }
        saleRepository.save(newSale);
        return Optional.of(newSale);
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Optional<Sale> getSaleById(Long id) {
        return saleRepository.findById(id);
    }

    public String getProfitsByDay(LocalDate date) {
        List<Sale> sales = saleRepository.findByDate(date);
        double profits = 0.0;
        for (Sale sale : sales) {
            List<SaleDetail> saleDetails = sale.getSaleDetails();
            for(SaleDetail saleDetail: saleDetails) {
                profits += saleDetail.getTotalProfit();
            }
        }
        return formatNumber(profits);
    }

    public String getProfitsByMonth(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        List<Sale> sales = saleRepository.findByDateBetween(startDate, endDate);
        double profits = 0.0;
        for (Sale sale : sales) {
            List<SaleDetail> saleDetails = sale.getSaleDetails();
            for(SaleDetail saleDetail: saleDetails) {
                profits += saleDetail.getTotalProfit();
            }
        }
        return formatNumber(profits);
    }

    public String getProfitsByYear(int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = startDate.plusYears(1).minusDays(1);
        List<Sale> sales = saleRepository.findByDateBetween(startDate, endDate);
        double profits = 0.0;
        for (Sale sale : sales) {
            List<SaleDetail> saleDetails = sale.getSaleDetails();
            for(SaleDetail saleDetail: saleDetails) {
                profits += saleDetail.getTotalProfit();
            }
        }
        return formatNumber(profits);
    }

    private String formatNumber(Double number) {
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(number);
    }

}
