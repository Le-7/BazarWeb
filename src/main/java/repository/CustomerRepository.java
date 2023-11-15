package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.Customer;
import model.Product;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // custom queries if needed
}

