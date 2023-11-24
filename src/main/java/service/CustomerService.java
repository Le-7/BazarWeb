package service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Customer;
import repository.CustomerRepository;

@Service
public class CustomerService {
	
	

	
	
	
    @Autowired
    private static CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void inscriptionCustomer(Customer customer) {
    	
    	if (customerRepository.existsByUsername(customer.getUsername())) {
            throw new DuplicateUsernameException("Username already exists: " + customer.getUsername());
        }

    	customerRepository.save(customer);
    }
    


    // Other methods...

    public static boolean connectionCustomer(String username, String password) {
        // Check if a customer with the given username and password exists in the database
        Optional<Customer> optionalCustomer = customerRepository.findByUsernameAndPassword(username, password);

        // If a customer is found, return true; otherwise, return false
        return optionalCustomer.isPresent();
    }

    
    
    
    // service methods
}
