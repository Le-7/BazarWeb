package service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Administrateur;
import repository.AdministrateurRepository;

@Service
public class AdministrateurService {

	
	
	
    @Autowired
    private static AdministrateurRepository administrateurRepository;

    @Autowired
    public AdministrateurService(AdministrateurRepository administrateurRepository) {
        this.administrateurRepository = administrateurRepository;
    }

	public boolean connectionAdministrateur(String username, String password) {
		Optional<Administrateur> optionalAdministrateur = administrateurRepository.findByUsernameAndPassword(username, password);

        // If a customer is found, return true; otherwise, return false
        return optionalAdministrateur.isPresent();
	}
    
    
}
