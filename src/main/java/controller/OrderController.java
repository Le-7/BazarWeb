package controller;

import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.OrderService;
import model.Order;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{userId}")
    public String getOrderById(@PathVariable Long userId, Model model) {
        Order order = orderService.getUserOrder(userId);
        model.addAttribute("order", order);
        model.addAttribute("userId", userId);
        return "orderDetails";
    }

    @GetMapping("/all")
    public List<Order> getAllOrders(@RequestParam Long userId) {
        return orderService.getAllOrders(userId);
    }

    @PostMapping("/addProductToCart")
    public ResponseEntity<String> addProductToCart(@RequestParam Long productId, @RequestParam Long userId) {
        try {
            orderService.addProductToCart(productId, userId);
            return ResponseEntity.ok("Product added to cart successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add product to cart!");
        }
    }
}
