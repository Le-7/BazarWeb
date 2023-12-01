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
    public String addProduct(@RequestParam String productName,
                             @RequestParam double productPrice,
                             @RequestParam String productImage) {
        Product newProduct = new Product(productName, productPrice, productImage);
        productService.addProduct(newProduct);
        return "redirect:/product/product";
    }
    @PostMapping("/addProductModo")
    public String addProductmodo(@RequestParam String productName,
                             @RequestParam double productPrice,
                             @RequestParam String productImage,@RequestParam String username,@RequestParam String password,Model model) {
        Product newProduct = new Product(productName, productPrice, productImage, username);
        productService.addProduct(newProduct);
        model.addAttribute("username",username);
        model.addAttribute("password",password);
        List<Product> products = productService.getAllProductsModo(username);
        model.addAttribute("products", products);
        return "productModo";
    }

    @PostMapping("/delete-product")
    public String deleteProduct(@RequestParam Long productId,Model model,@RequestParam String username,@RequestParam String password) {
        productService.deleteProduct(productId);
        List<Product> products = productService.getAllProductsModo(username);
        model.addAttribute("products", products);
        model.addAttribute("username",username);
        model.addAttribute("password",password);

        
        return "productModo";
    }

    @GetMapping("/product")
    public String showProducts(Model model) {
    	
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product";
    }
    @PostMapping("/productModo")
    public String showProductsModo(Model model,@RequestParam String username,@RequestParam String password) {
        List<Product> products = productService.getAllProductsModo(username);
        model.addAttribute("products", products);
        model.addAttribute("username",username);
        model.addAttribute("password",password);

        return "productModo";
    }
   

}
