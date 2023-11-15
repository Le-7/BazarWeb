package controller;

import service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    // controller methods
}