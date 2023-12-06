package repository;

import org.springframework.stereotype.Repository;

import model.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByCustomerId(Long customerId);
}

