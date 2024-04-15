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

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/sales")
public class SaleController {

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
            sale.setDate(LocalDate.of(2023, (1 + random.nextInt(12)), (1 + random.nextInt(29))));
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

    @GetMapping
    public ResponseEntity<?> getSales() {
        log.info("GET /sales");
        try {
            return ResponseEntity.ok(saleService.getAllSales());
        } catch (DataAccessException e) {
            log.info("Error al acceder a la base de datos");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Error al acceder a la base de datos"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSaleById(@PathVariable Long id) {
        log.info("GET /sales/" + id);

        if (id == null || id <= 0) {
            log.info("Error de parámetro");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("El ID de la venta no es válido"));
        }

        try {
            Optional<Sale> optionalSale = saleService.getSaleById(id);
            if (!optionalSale.isPresent()) {
                log.info("No se encontro el registro " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("No se encontró ninguna publicación con el ID proporcionado"));
                
            } 

            Sale sale = optionalSale.get();
            return ResponseEntity.ok(sale);
        } catch (DataAccessException e) {
            log.info("Error al acceder a la base de datos");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Error al acceder a la base de datos"));
        }
    }

    @GetMapping("/profits/day/{date}")
    public ResponseEntity<?> getProfitsByDay(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("GET /profit/day/" + date);
        
        try {
            String profit = saleService.getProfitsByDay(date);
            return ResponseEntity.ok(new MessageResponse("Las ganancias para " + date + " son de: " + profit));
        } catch (DataAccessException e) {
            log.info("Error al acceder a la base de datos");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Error al acceder a la base de datos"));
        }
    }

    @GetMapping("/profits/month/{year}/{month}")
    public ResponseEntity<?> getProfitsByMonth(@PathVariable("year") int year, @PathVariable("month") int month) {
        log.info("GET /profit/month/" + year + "/" + month);
        
        try {
            String profit = saleService.getProfitsByMonth(year, month);
            return ResponseEntity.ok(new MessageResponse("Las ganancias para " + month + "/" + year + " son de: " + profit));
        } catch (DataAccessException e) {
            log.info("Error al acceder a la base de datos");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Error al acceder a la base de datos"));
        }
    }

    @GetMapping("/profits/year/{year}")
    public ResponseEntity<?> getProfitsByYear(@PathVariable("year") int year) {
        log.info("GET /profit/year/" + year);
        
        try {
            String profit = saleService.getProfitsByYear(year);
            return ResponseEntity.ok(new MessageResponse("Las ganancias para " + year + " son de: " + profit));
        } catch (DataAccessException e) {
            log.info("Error al acceder a la base de datos");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Error al acceder a la base de datos"));
        }
    }

    static class MessageResponse {
        private final String message;
    
        public MessageResponse(String message) {
            this.message = message;
        }
    
        public String getMessage() {
            return message;
        }
    }
}
