package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public String addCustomer(@RequestParam String username, @RequestParam String password, @RequestParam String mail) {
		// Create a new product instance
		Customer newCustomer = new Customer(username, password, mail);

		// Add the user to the database
		customerService.inscriptionCustomer(newCustomer);

		// Redirect to the signup page after the signup
		return "inscription";
	}

	@PostMapping("/connected")
	public String ConnectionCustomer(@RequestParam String username, @RequestParam String password) {
		// Create a new product instance
		//Customer newCustomer = new Customer(username, password);

		
		// If a customer is found, return true; otherwise, return false
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
	public String pageConnected(Model model) {
       
        return "connected";
    }
}