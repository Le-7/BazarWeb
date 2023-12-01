package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import model.Customer;
import service.AdministrateurService;
import service.CustomerService;

@Controller
@RequestMapping("/administrateur")
public class AdministrateurController {

	@Autowired
	private AdministrateurService administrateurService;
	private CustomerService customerservice ;

	
	public AdministrateurController(AdministrateurService administrateurService) {
		super();
		this.administrateurService = administrateurService;
	}

	@GetMapping("/connection")
	public String pageConnection() {
		return "connectionAdmin";
	}
	
	
	@PostMapping("/infoClient")
	public String pageConnectionInfoClient(@RequestParam String username) {
		return "infoClient";
	}
	
	@PostMapping("/connected")
	public String ConnectionCustomer(@RequestParam String username, @RequestParam String password,Model model) {
		if(administrateurService.connectionAdministrateur(username,password)) {
			  // Assuming you have a service class that retrieves the list of clients from the database
	        List<Customer> clients = CustomerService.getAllClients();

	        // Add the list of clients to the model
	        model.addAttribute("clients", clients);

	        //return "yourPage";
			
			return "adminConnected";
		}else{
			return "connectionAdmin";
		}
	}
}
