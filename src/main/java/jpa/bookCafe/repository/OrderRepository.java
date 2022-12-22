package jpa.bookCafe.repository;

import jpa.bookCafe.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o join fetch o.payment")
    public List<Order> findByAll();

}
