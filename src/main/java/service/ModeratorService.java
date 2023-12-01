package service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Moderator;
import repository.ModeratorRepository;

@Service
public class ModeratorService {
    @Autowired
    private ModeratorRepository moderatorRepository;

	public List<Moderator> getAllModerators() {
		// TODO Auto-generated method stub
		return moderatorRepository.findAll();
		
	}

	public Moderator getModoByUsername(String username) {
		// TODO Auto-generated method stub
        return moderatorRepository.findByUsername(username);
	}

	public void deleteModerateur(Moderator moderateur) {
		// TODO Auto-generated method stub
		if (moderateur != null) {
			moderatorRepository.delete(moderateur);
        }
		
	}

    // service methods
}