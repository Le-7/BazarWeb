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
	public String ConnectionCustomer(@RequestParam String username, @RequestParam String password) {
		if(customerService.connectionCustomer(username,password)) {
			return "redirect:/customer/connected";
		}else{
			return "redirect:/customer/inscription";
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
	public String pageConnected() {
       
        return "connected";
    }
}