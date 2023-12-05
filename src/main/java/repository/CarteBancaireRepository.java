package repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.CarteBancaire;

public interface CarteBancaireRepository extends JpaRepository<CarteBancaire, Long> {
	    
	    CarteBancaire findByNumeroCarte(String numeroCarte);
}

