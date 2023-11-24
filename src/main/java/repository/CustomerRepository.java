package repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // custom queries if needed
    Optional<Customer> findByUsernameAndPassword(String username, String password);
    //boolean existsByUsername(String username);
	//boolean existsByUsername(String username);
	boolean existsByUsername(String username);
}

