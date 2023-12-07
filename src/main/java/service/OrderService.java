package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.OrderRepository;
import repository.ProductRepository;
import repository.CustomerRepository;
import java.util.List;
import java.util.ArrayList;

import model.Customer;
import model.Order;
import model.Product;



@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    @Autowired
    public OrderService(
            OrderRepository orderRepository,
            ProductRepository productRepository,
            CustomerService customerService,
            CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Order createOrder(List<Long> productIds, Long customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        List<Product> products = productRepository.findAllById(productIds);

        if (areProductsInStock(products)) {
            double totalAmount = calculateTotalAmount(products);
            Order order = new Order(customer, products, totalAmount);
            updateProductStock(products);
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Certains produits ne sont pas disponibles en stock.");
        }
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }
    
    public Order getUserOrder(Long userId) {
        // Try to find an existing order for the user
        List<Order> userOrders = orderRepository.findByCustomerId(userId);

        if (!userOrders.isEmpty()) {
            // If the user has existing orders, return the first one (assuming one user has one active order at a time)
            return userOrders.get(0);
        } else {
            // If the user does not have an existing order, create a new one
            Customer customer = customerRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
            Order newOrder = new Order(customer, new ArrayList<>(), 0.0);
            return orderRepository.save(newOrder);
        }
    }


    public List<Order> getAllOrders(Long userId) {
        // Check if the user exists
        if (customerRepository.existsById(userId)) {
            // Retrieve orders associated with the given user ID
            return orderRepository.findByCustomerId(userId);
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }

    public void addProductToCart(Long productId, Long userId) {
        // Retrieve or create a user's order
        Order userOrder = getUserOrder(userId);

        // Retrieve the product to be added to the cart
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        if (isProductInStock(product)) {
            // Add the product to the user's order
            userOrder.getProducts().add(product);

            // Update the total amount of the order
            double newTotalAmount = userOrder.getTotalAmount() + product.getPrice();
            userOrder.setTotalAmount(newTotalAmount);

            // Update the stock of the product
            updateProductStock(List.of(product));

            // Save the user's order
            orderRepository.save(userOrder);
        } else {
            throw new RuntimeException("Le produit n'est pas disponible en stock.");
        }
    }


    private double calculateTotalAmount(List<Product> products) {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    private boolean areProductsInStock(List<Product> products) {
        return products.stream().allMatch(this::isProductInStock);
    }

    private boolean isProductInStock(Product product) {
        return product.getStock() >= 1;
    }

    private void updateProductStock(List<Product> products) {
        products.forEach(product -> {
            int currentStock = product.getStock();
            if (currentStock >= 1) {
                product.setStock(currentStock - 1);
                productRepository.save(product);
            }
        });
    }
   
    @Transactional
    public void clearUserCart(Long userId) {
        // Retrieve the user's order
        Order userOrder = getUserOrder(userId);

        // Retrieve products from the user's order
        List<Product> productsInCart = userOrder.getProducts();

        // Restore stock for each product in the user's cart
        productsInCart.forEach(product -> {
            int currentStock = product.getStock();
            product.setStock(currentStock - 1);
            productRepository.save(product);
        });

        // Clear the user's order (remove products and reset total amount)
        userOrder.getProducts().clear();
        userOrder.setTotalAmount(0.0);

        // Save the updated user's order
        orderRepository.save(userOrder);
    }
}
