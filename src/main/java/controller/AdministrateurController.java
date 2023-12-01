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
import model.Moderator;
import service.AdministrateurService;
import service.CustomerService;
import service.ModeratorService;

@Controller
@RequestMapping("/administrateur")
public class AdministrateurController {

	@Autowired
	private AdministrateurService administrateurService;
	private CustomerService customerservice ;
	private ModeratorService moderatorservice;

	
	public AdministrateurController(AdministrateurService administrateurService,CustomerService customerservice,ModeratorService moderatorservice) {
		super();
		this.administrateurService = administrateurService;
		this.customerservice=customerservice;
		this.moderatorservice=moderatorservice;
	}

	@GetMapping("/connection")
	public String pageConnection() {
		return "connectionAdmin";
	}
	
	
	@PostMapping("/infoClient")
	public String pageConnectionInfoClient(@RequestParam String username,Model model) {
		Customer client = customerservice.getClientByUsername(username);

        model.addAttribute("client", client);
		return "infoClient";
	}
	
	@PostMapping("/SupprimerClient")
	public String SupressClient(@RequestParam String username,Model model) {
		Customer client = customerservice.getClientByUsername(username);
		 
		if (client != null) {
            customerservice.deleteClient(client);

            model.addAttribute("message", "Client supprimé avec succès");
        } else {
            model.addAttribute("error", "Impossible de trouver le client avec le nom d'utilisateur : " + username);
        }
		List<Customer> clients = CustomerService.getAllClients();

        model.addAttribute("clients", clients);
    	List<Moderator> moderateurs = moderatorservice.getAllModerators();
        model.addAttribute("moderateurs", moderateurs);

		return "adminConnected";
	}
	
	@PostMapping("/SupprimerModerateur")
	public String SupressModo(@RequestParam String username,Model model) {
		Moderator moderateur = moderatorservice.getModoByUsername(username);
		 
		if (moderateur != null) {
            moderatorservice.deleteModerateur(moderateur);

            model.addAttribute("message", "Client supprimé avec succès");
        } else {
            model.addAttribute("error", "Impossible de trouver le client avec le nom d'utilisateur : " + username);
        }
		List<Customer> clients = CustomerService.getAllClients();

        model.addAttribute("clients", clients);
    	List<Moderator> moderateurs = moderatorservice.getAllModerators();
        model.addAttribute("moderateurs", moderateurs);

		return "adminConnected";
	}
	
	@PostMapping("/infoModo")
	public String pageConnectionInfoModo(@RequestParam String username,Model model) {
		Moderator moderateur = moderatorservice.getModoByUsername(username);

        model.addAttribute("moderateur", moderateur);
		return "infoModerateur";
	}
	
	@PostMapping("/connected")
	public String ConnectionCustomer(@RequestParam String username, @RequestParam String password,Model model) {
		if(administrateurService.connectionAdministrateur(username,password)) {
			  // Assuming you have a service class that retrieves the list of clients from the database
	        List<Customer> clients = CustomerService.getAllClients();
			List<Moderator> moderateurs = moderatorservice.getAllModerators();
			System.out.println("moderateurs "+moderateurs);
	        model.addAttribute("moderateurs", moderateurs);
	        model.addAttribute("clients", clients);

			
			return "adminConnected";
		}else{
			return "connectionAdmin";
		}
	}
}
