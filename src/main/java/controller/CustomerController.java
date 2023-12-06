package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.DuplicateUsernameException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import model.Customer;
import service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	// private final ProductService productService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;

	}

	@PostMapping("/inscris")
	public String addCustomer(@RequestParam String username, @RequestParam String password, @RequestParam String mail, Model model) {
		try {
            // Create a new customer instance
            Customer newCustomer = new Customer(username, password, mail);

            // Add the customer to the database
            customerService.inscriptionCustomer(newCustomer);

            model.addAttribute("username", username);

            // Redirect to the signup page after the signup
            return "inscris";
        } catch (DuplicateUsernameException e) {
            // Handle the case where a duplicate username is detected
            model.addAttribute("error", e.getMessage());
            return "inscription"; // Return to the inscription page with an error message
        }
	}

	@PostMapping("/connected")
	public String ConnectionCustomer(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
		if(customerService.connectionCustomer(username,password)) {
			Long userId = customerService.getClientByUsername(username).getId();
		    session.setAttribute("userId", userId);
		    model.addAttribute("userId", userId);
			return "redirect:/customer/connected";
		}else{
			return "redirect:/customer/inscription?error=1";
		}
	}

	@GetMapping("/inscription")
	public String pageInscription() {
		return "inscription";
	}
	@GetMapping("/connection")
	public String pageConnection() {
		return "connection";
	}
	
	@GetMapping("/connected")
	public String pageConnected(Model model, HttpServletRequest request) {
		Long userId = (Long) request.getSession().getAttribute("userId");
		model.addAttribute("userId", userId);
        return "connected";
    }
	 @GetMapping("/showuserinfo")
	    public String showUserInfo(HttpSession session, Model model) {
	        // Vérifier si l'utilisateur est connecté en vérifiant la présence de "userId" dans la session
	        Long userId = (Long) session.getAttribute("userId");

	        if (userId != null) {
	            // Si l'utilisateur est connecté, récupérer les informations du client
	            Customer user = customerService.getCustomerById(userId);

	            // Ajouter les informations du client au modèle
	            model.addAttribute("user", user);
	            model.addAttribute("userId", userId);
	            // Retourner la vue pour afficher les informations de l'utilisateur
	            return "showuserinfo";
	        } else {
	            // Si l'utilisateur n'est pas connecté, rediriger vers la page de connexion
	            return "redirect:/customer/connection";
	        }
	    }

}