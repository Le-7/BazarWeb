package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import service.ProductService;
import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    private final ProductService productService;

    @Autowired
    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
    	Long userId = (Long) request.getSession().getAttribute("userId");
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("userId", userId);
        return "index"; 
    }
}
