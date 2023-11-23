package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;  // Import List
import model.Product;  // Import Product entity
import repository.ProductRepository;  // Import ProductRepository

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void addProduct(Product product) {
 
        productRepository.save(product);
    }
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
