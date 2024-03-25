package duoc.s3_d;

import java.util.List;
import java.time.LocalDate;

public class Sale {
    
    private Long id;
    private LocalDate date;
    private List<SaleDetail> saleDetails;

    public Sale(Long id, LocalDate date, List<SaleDetail> saleDetail) {
        this.id = id;
        this.date = date;
        this.saleDetails = saleDetail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<SaleDetail> getSaleDetails() {
        return saleDetails;
    }

    public void setSaleDetails(List<SaleDetail> saleDetails) {
        this.saleDetails = saleDetails;
    }
}
