package jpa.bookCafe.repository;

import jpa.bookCafe.domain.Order;
import jpa.bookCafe.domain.Payment;
import jpa.bookCafe.kakaoPay.ApproveResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PaymentRepositoryTest {
    @Autowired OrderRepository orderRepository;
    @Autowired PaymentRepository paymentRepository;

    @BeforeEach
    void setup(){
        Order order1 = new Order();
        Order order2 = new Order();
        Order order3 = new Order();
        Order order4 = new Order();

        String 하루전 = LocalDateTime.now().minusDays(1).toString();
        String 이틀전 = LocalDateTime.now().minusDays(2).toString();
        String 일주일전 = LocalDateTime.now().minusDays(7).toString();
        String 한달전 = LocalDateTime.now().minusDays(31).toString();

        Payment payment1 = Payment.createPayment(order1, approveResponse(하루전));
        Payment payment2 = Payment.createPayment(order2, approveResponse(이틀전));
        Payment payment3 = Payment.createPayment(order3, approveResponse(일주일전));
        Payment payment4 = Payment.createPayment(order4, approveResponse(한달전));


        paymentRepository.save(payment1);
        paymentRepository.save(payment2);
        paymentRepository.save(payment3);
        paymentRepository.save(payment4);
        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);
        orderRepository.save(order4);
    }

    @DisplayName("전체결제조회")
    @Test
    void findAll() {
        //when
        List<Payment> all = paymentRepository.findAll();

        //then
        Assertions.assertThat(all.size()).isEqualTo(4);
    }

    @DisplayName("기간결제조회")
    @Test
    void findAllBetweenDays() {
        LocalDateTime start = LocalDateTime.of(LocalDate.now().minusDays(15), LocalTime.of(0,0,0));
        LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));
        //when
        List<Payment> all = paymentRepository.findAllBetweenDays(start,end);

        //then
        Assertions.assertThat(all.size()).isEqualTo(3);
    }

    ApproveResponse approveResponse(String dateTime){
        ApproveResponse approveResponse = new ApproveResponse();
        approveResponse.setApproved_at(dateTime);
        approveResponse.setCreated_at(dateTime);
        approveResponse.setItem_name("itemName");
        approveResponse.setTid("tid");
        return approveResponse;
    }
}