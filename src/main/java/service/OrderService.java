package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.OrderRepository;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    @Transactional
    public Order createOrder(List<Long> productIds) {
        List<Product> products = productService.getAllProductsByIds(productIds);

        if (areProductsInStock(products)) {
            double totalAmount = calculateTotalAmount(products);
            Order order = new Order(products, totalAmount);
            updateProductStock(products);
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Certains produits ne sont pas disponibles en stock.");
        }
    }

    private double calculateTotalAmount(List<Product> products) {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    private boolean areProductsInStock(List<Product> products) {
        return products.stream().allMatch(this::isProductInStock);
    }

    private boolean isProductInStock(Product product) {
        return product.getStockQuantity() >= 1;
    }

    private void updateProductStock(List<Product> products) {
        products.forEach(product -> {
            int currentStock = product.getStockQuantity();
            if (currentStock >= 1) {
                product.setStockQuantity(currentStock - 1);
                productService.updateProduct(product);
            }
        });
    }
}
