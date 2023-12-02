package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.*;
import org.springframework.ui.Model;
import model.Product;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private ProductService productService; // Inject your ProductService

    @GetMapping("/search")
    public String searchProducts(@RequestParam String keyword, Model model) {
        List<Product> searchResults = productService.searchProducts(keyword);
        model.addAttribute("products", searchResults);
        return "searchResult"; 
    }
}

