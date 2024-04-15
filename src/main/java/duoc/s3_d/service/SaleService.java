package duoc.s3_d.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.text.DecimalFormat;

import duoc.s3_d.repository.SaleRepository;
import duoc.s3_d.model.Sale;
import duoc.s3_d.model.SaleDetail;

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
