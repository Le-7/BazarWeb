package repository;

import org.springframework.stereotype.Repository;

import model.Moderator;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ModeratorRepository extends JpaRepository<Moderator, Long> {
    // custom queries if needed
}

