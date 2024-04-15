package duoc.s3_d.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.dao.DataAccessException; 

import duoc.s3_d.model.Product;
import duoc.s3_d.model.Sale;
import duoc.s3_d.model.SaleDetail;
import duoc.s3_d.service.SaleService;
import duoc.s3_d.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/sales")
public class SaleController {

    // private ArrayList<Sale> sales = new ArrayList<Sale>();

    // public SaleController() {

    //     Product product1 = new Product(1L, "Casita de Perro", 10.0, 15.0);
    //     Product product2 = new Product(2L, "Hueso", 8.0, 12.0);
    //     Product product3 = new Product(3L, "Cepillo para gato", 8.0, 12.0);

    //     sales.add(new Sale(1L, LocalDate.of(2023, 12, 20), List.of(new SaleDetail(product1, 2))));
    //     sales.add(new Sale(2L, LocalDate.of(2024, 1, 3), List.of(new SaleDetail(product1, 3),new SaleDetail(product2, 1),new SaleDetail(product3, 1))));
    //     sales.add(new Sale(3L, LocalDate.of(2024, 1, 10), List.of(new SaleDetail(product2, 2),new SaleDetail(product3, 1))));
    //     sales.add(new Sale(4L, LocalDate.of(2024, 2, 14), List.of(new SaleDetail(product1, 4),new SaleDetail(product3, 3))));
    //     sales.add(new Sale(5L, LocalDate.of(2024, 2, 14), List.of(new SaleDetail(product2, 2),new SaleDetail(product3, 1))));
    //     sales.add(new Sale(6L, LocalDate.of(2024, 3, 22), List.of(new SaleDetail(product1, 1),new SaleDetail(product2, 2))));
    //     sales.add(new Sale(7L, LocalDate.of(2024, 3, 23), List.of(new SaleDetail(product2, 1),new SaleDetail(product3, 3))));
    //     sales.add(new Sale(8L, LocalDate.of(2024, 3, 24), List.of(new SaleDetail(product3, 4))));
    // }

    
    private static final Logger log = LoggerFactory.getLogger(SaleController.class);

    @Autowired
    private SaleService saleService;
    @Autowired
    private ProductService productService;

    @PostConstruct
    public void initializeData() {

        Random random = new Random();
        for (int i = 1; i <= 10; i++) {
            Product product = new Product();
            product.setName("Product " + i);
            Double costo = 1000 + random.nextDouble() * 29000;
            product.setCostPrice(costo);
            product.setSellingPrice(costo*1.2);
            productService.saveProduct(product);
        }


        for (int i = 1; i <= 100; i++) {
            Sale sale = new Sale();
            sale.setDate(LocalDate.of(2024, (1 + random.nextInt(12)), (1 + random.nextInt(29))));
            Integer cantidadProductos = (1 + random.nextInt(10));
            for (int j = 0; j < cantidadProductos; j++) {
                Product product = productService.getRandomProduct();
                SaleDetail saleDetail = new SaleDetail();
                saleDetail.setProduct(product);
                saleDetail.setQuantity(1 + random.nextInt(10));
                sale.addSaleDetail(saleDetail);
            }

            saleService.saveSale(sale);
        }

    }

    // @GetMapping
    // public ArrayList<Sale> getSales() {
    //     System.out.println("Respondiendo sales");
    //     return sales;
    // }

    // @GetMapping("/{id}")
    // public Sale getSaleById(@PathVariable Long id) {
    //     for (Sale sale : sales) {
    //         if (sale.getId() == id) {
    //             System.out.println("Respondiendo sales " + id);
    //             return sale;
    //         }
    //     }
    //     return null;
    // }

    // @GetMapping("/profits/day/{date}")
    // public Map<String, Object> getProfitsByDay(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
    //     double profits = 0.0;
    //     for (Sale sale : sales) {
    //         if (sale.getDate().isEqual(date)) {
    //             List<SaleDetail> saleDetails = sale.getSaleDetails();
    //             for(SaleDetail saleDetail: saleDetails) {
    //                 profits += saleDetail.getTotalProfit();
    //             }
    //         }
    //     }
    //     System.out.println("Respondiendo ganancias diarias " + date);
    //     return responseFormat(profits);
    // }

    // @GetMapping("/profits/month/{year}/{month}")
    // public Map<String, Object> getProfitsByMonth(@PathVariable("year") int year, @PathVariable("month") int month) {
    //     double profits = 0.0;
    //     for (Sale sale : sales) {
    //         if (sale.getDate().getYear() == year && sale.getDate().getMonthValue() == month) {
    //             List<SaleDetail> saleDetails = sale.getSaleDetails();
    //             for(SaleDetail saleDetail: saleDetails) {
    //                 profits += saleDetail.getTotalProfit();
    //             }
    //         }
    //     }
    //     System.out.println("Respondiendo ganancias mensual " + month + ", " + year);
    //     return responseFormat(profits);
    // }

    // @GetMapping("/profits/year/{year}")
    // public Map<String, Object> getProfitsByYear(@PathVariable("year") int year) {
    //     double profits = 0.0;
    //     for (Sale sale : sales) {
    //         if (sale.getDate().getYear() == year) {
    //             List<SaleDetail> saleDetails = sale.getSaleDetails();
    //             for(SaleDetail saleDetail: saleDetails) {
    //                 profits += saleDetail.getTotalProfit();
    //             }
    //         }
    //     }
    //     System.out.println("Respondiendo ganancias anual " + year);
    //     return responseFormat(profits);
    // }

    // private Map<String, Object> responseFormat(Double number) {
    //     Map<String, Object> response = new HashMap<>();
    //     response.put("profits", formatNumber(number));
    //     return response;
    // }

    // private String formatNumber(Double number) {
    //     DecimalFormat df = new DecimalFormat("0.0");
    //     return df.format(number);
    // }
}
