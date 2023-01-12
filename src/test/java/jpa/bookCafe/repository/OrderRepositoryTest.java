package jpa.bookCafe.repository;

import jpa.bookCafe.domain.Order;
import jpa.bookCafe.domain.Payment;
import jpa.bookCafe.kakaoPay.ApproveResponse;
import org.aspectj.weaver.ast.Or;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrderRepositoryTest {
    @Autowired OrderRepository orderRepository;
    @Autowired PaymentRepository paymentRepository;

    @DisplayName("결제건만 조회")
    @Test
    void findByAll() {
        //given
        Order order1 = new Order();
        Order order2 = new Order();
        Order order3 = new Order();
        Payment payment1 = Payment.createPayment(order1, approveResponse());//결제 총 2건
        Payment payment2 = Payment.createPayment(order2, approveResponse());

        paymentRepository.save(payment1);
        paymentRepository.save(payment2);
        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);


        //when
        List<Order> byAll = orderRepository.findByAll();

        //then
        Assertions.assertThat(byAll.size()).isEqualTo(2);
    }

    ApproveResponse approveResponse(){
        ApproveResponse approveResponse = new ApproveResponse();
        approveResponse.setApproved_at(LocalDateTime.now().toString());
        approveResponse.setCreated_at(LocalDateTime.now().toString());
        approveResponse.setItem_name("itemName");
        approveResponse.setTid("tid");
        return approveResponse;
    }
}