package controller;

import service.ModeratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ModeratorController {
    @Autowired
    private ModeratorService moderatorService;
    
    // controller methods
}