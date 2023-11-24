package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import service.AdministrateurService;

@Controller
@RequestMapping("/administrateur")
public class AdministrateurController {

	@Autowired
	private AdministrateurService administrateurService;
	
	
	public AdministrateurController(AdministrateurService administrateurService) {
		super();
		this.administrateurService = administrateurService;
	}

	@GetMapping("/connection")
	public String pageConnection() {
		return "connectionAdmin";
	}
	
	@PostMapping("/connected")
	public String ConnectionCustomer(@RequestParam String username, @RequestParam String password) {
		if(administrateurService.connectionAdministrateur(username,password)) {
			return "redirect:/product/product";
		}else{
			return "redirect:/#";
		}
	}
}
