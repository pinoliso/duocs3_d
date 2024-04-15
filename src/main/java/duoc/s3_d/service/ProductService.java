package duoc.s3_d.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

import duoc.s3_d.repository.ProductRepository;
import duoc.s3_d.model.Product;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getRandomProduct() {
        List<Product> productList = getAllProducts();
        Random random = new Random();
        int randomIndex = random.nextInt(productList.size());
        return productList.get(randomIndex);
    }

}
