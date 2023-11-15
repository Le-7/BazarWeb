package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.OrderService;

@RestController
@RequestMapping("/api/products")
public class OrderController {
    @Autowired
    private OrderService orderService;
    // controller methods
}