package controller;

import model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.ProductService;
import java.util.List;


@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add-product")
    public String addProduct(@RequestParam String productName, @RequestParam double productPrice) {
        // Create a new product instance
        Product newProduct = new Product(productName, productPrice);

        // Add the product to the database
        productService.addProduct(newProduct);

        // Redirect to the home page after adding the product
        return "redirect:/";
    }
    
    @PostMapping("/delete-product")
    public String deleteProduct(@RequestParam Long productId) {
        // Use the ProductService to delete the product by ID
        productService.deleteProduct(productId);

        // Redirect to the home page after deleting the product
        return "redirect:/";
    }
    @GetMapping("/")
    public String showProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product";
    }
}
