package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import service.ModeratorService;

@Controller
@RequestMapping("/moderator")
public class ModeratorController {
    @Autowired
    private ModeratorService moderatorService;
    
    // controller methods
	@PostMapping("/connected")
	public String Connectionmoderator(@RequestParam String username, @RequestParam String password,Model model) {
		if(moderatorService.connectionmoderator(username,password)) {
			model.addAttribute(username);
			return "redirect:/product/productModo";
		}else{
			return "redirect:/moderator/inscription";
		}
	}

	@GetMapping("/inscription")
	public String pageInscription() {
		return "InscriptionModerator";
	}
	@GetMapping("/connection")
	public String pageConnection() {
		return "connectionModerator";
	}
    
}