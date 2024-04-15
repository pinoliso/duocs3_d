package duoc.s3_d.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;

@Data
@Entity
@Table(name = "products")
public class Product {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double costPrice;
    private double sellingPrice;

    @ManyToMany(mappedBy = "products")
    private Set<Sale> sales;
}