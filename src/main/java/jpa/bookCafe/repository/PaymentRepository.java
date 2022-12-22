package jpa.bookCafe.repository;

import jpa.bookCafe.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    @Query("select p from Payment p join fetch p.order")
    List<Payment> findAll();

    @Query("select p from Payment p join fetch p.order where p.paymentTime between :startDate and :endDate")
    List<Payment> findAllBetweenDays(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
