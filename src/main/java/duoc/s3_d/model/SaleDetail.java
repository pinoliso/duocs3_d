package duoc.s3_d.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import java.util.List;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "sales_details")
public class SaleDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "sale_id")
    @JsonIgnore
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public double getProfit() {
        return product.getSellingPrice() - product.getCostPrice();
    }

    public double getTotalProfit() {
        return getProfit() * quantity;
    }
}