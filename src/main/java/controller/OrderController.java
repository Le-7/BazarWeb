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
import service.CustomerService;
import service.CarteBancaireService;
import service.EmailService;
import model.Order;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final CarteBancaireService carteBancaireService;
    private final CustomerService customerService;
    private final EmailService emailService;

    @Autowired
    public OrderController(OrderService orderService, CarteBancaireService carteBancaireService,CustomerService customerService, EmailService emailservice) {
        this.orderService = orderService;
        this.carteBancaireService = carteBancaireService;
        this.customerService = customerService;
        this.emailService = emailservice;
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
    
    @PostMapping("/payment")
    public String processPayment(@RequestParam String cardNumber,
                                 @RequestParam int securityCode,
                                 @RequestParam long userId,
                                 @RequestParam String expiryDate,
                                 @RequestParam double amount,
                                 Model model) {

        String cardHolderName = customerService.getCustomerById(userId).getUsername();
        // Vérifier si la carte existe
        if (carteBancaireService.mettreAJourSolde(cardNumber, securityCode, amount, cardHolderName, expiryDate) != 404) {
       
            model.addAttribute("message", "Paiement réussi!");
            model.addAttribute("amountPaid", amount);
            model.addAttribute("cardNumber", cardNumber);
            model.addAttribute("userId",userId);
            emailService.sendEmail(customerService.getCustomerById(userId).getEmail(), "Paiement confirmé !", "Merci pour votre achat "+ cardHolderName+", vous avez payé : "+ amount+ " dollars");
                  
            orderService.clearUserCart(userId);
            return "paymentConfirmation";
        } else {
          
        	model.addAttribute("userId",userId);
            model.addAttribute("error", "La carte de crédit n'existe pas ou les détails sont incorrects.");
            return "paymentError"; // Page d'erreur de paiement
        }
    }
}
