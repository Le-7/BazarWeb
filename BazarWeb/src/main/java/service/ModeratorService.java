package service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ModeratorRepository;

@Service
public class ModeratorService {
    @Autowired
    private ModeratorRepository moderatorRepository;

    // service methods
}