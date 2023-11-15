package repository;

import org.springframework.stereotype.Repository;

import model.Order;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // custom queries if needed
}

