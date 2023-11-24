package repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Administrateur;

public interface AdministrateurRepository extends JpaRepository<Administrateur, Long>{

	Optional<Administrateur> findByUsernameAndPassword(String username, String password);

}
