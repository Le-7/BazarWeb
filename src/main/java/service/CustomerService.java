package service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Customer;
import model.Moderator;
import repository.CustomerRepository;

@Service
public class CustomerService {	
	
    @Autowired
    private static CustomerRepository customerRepository;
    private final EmailService emailService;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, EmailService emailService) {
        CustomerService.customerRepository = customerRepository;
        this.emailService = emailService;
        
    }
    public Customer getClientByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    public void inscriptionCustomer(Customer customer) {
    	
    	if (customerRepository.existsByUsername(customer.getUsername())) {
            throw new DuplicateUsernameException("Username already exists: " + customer.getUsername());
        }

    	customerRepository.save(customer);
	   	emailService.sendEmail(customer.getEmail(), "Merci de votre inscription !", "Merci beaucoup "+customer.getUsername()+" de votre inscription chez BazarWeb !");
    }
    


    // Other methods...

    public boolean connectionCustomer(String username, String password) {
        // Check if a customer with the given username and password exists in the database
        Optional<Customer> optionalCustomer = customerRepository.findByUsernameAndPassword(username, password);

        // If a customer is found, return true; otherwise, return false
        return optionalCustomer.isPresent();
    }

	public static List<Customer> getAllClients() {
		return customerRepository.findAll();
		// TODO Auto-generated method stub
	}
	public void deleteClient(Customer client) {
		// TODO Auto-generated method stub
		 if (client != null) {
	            customerRepository.delete(client);
	        }
	}


    
    
    
    // service methods
}
