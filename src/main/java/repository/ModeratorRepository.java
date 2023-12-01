package repository;

import org.springframework.stereotype.Repository;

import model.Moderator;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ModeratorRepository extends JpaRepository<Moderator, Long> {

	Moderator findByUsername(String username);
     // custom queries if needed

	 Optional<Moderator> findByUsernameAndPassword(String username, String password);

	
}

