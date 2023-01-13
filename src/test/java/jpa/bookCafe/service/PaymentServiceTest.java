package jpa.bookCafe.service;

import jpa.bookCafe.domain.Order;
import jpa.bookCafe.domain.Payment;
import jpa.bookCafe.domain.enumStatus.OrderStatus;
import jpa.bookCafe.dto.PaymentDto;
import jpa.bookCafe.kakaoPay.ApproveResponse;
import jpa.bookCafe.repository.OrderRepository;
import jpa.bookCafe.repository.PaymentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {PaymentService.class})
class PaymentServiceTest {
    @Autowired PaymentService paymentService;
    @MockBean PaymentRepository paymentRepository;
    @MockBean OrderRepository orderRepository;

    @Test
    void 결제생성() {
        //given
        Order order = new Order();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(paymentRepository.save(any(Payment.class))).thenAnswer((Answer<Payment>) i->{
            Payment payment = i.getArgument(0);
            payment.setId(2L);
            return payment;
        });

        //when
        Long 결제생성 = paymentService.결제생성(1L, approveResponse(1));

        //then
        Assertions.assertThat(결제생성).isEqualTo(2L);
    }

    @Test
    void 전체결제() {
        //given
        List<Payment> list = Payments();
        when(paymentRepository.findAll()).thenReturn(list);

        //when
        List<PaymentDto> dtos = paymentService.전체결제();

        //then
        Assertions.assertThat(dtos.size()).isEqualTo(10);
    }


    @Test
    void 기간조회() {
        //given
        LocalDateTime start = LocalDateTime.of(LocalDate.now().minusDays(10), LocalTime.of(0,0,0));
        LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));
        List<Payment> list = Payments();
        when(paymentRepository.findAllBetweenDays(start,end)).thenReturn(list);

        //when
        List<PaymentDto> dtos = paymentService.기간조회(10);

        //then
        Assertions.assertThat(dtos.size()).isEqualTo(10);
    }

    @Test
    void 전체기간조회() {
        //given
        List<Payment> list = Payments();
        when(paymentRepository.findAll()).thenReturn(list);

        //when
        List<PaymentDto> dtos = paymentService.기간조회(null);

        //then
        Assertions.assertThat(dtos.size()).isEqualTo(10);
    }

    private List<Payment> Payments() {
        List<Payment> list = new ArrayList<>();
        for(int i=0; i<10; i++){
            Order order = new Order();
            order.setStatus(OrderStatus.결제대기);
            order.setId((long)i);
            list.add(Payment.createPayment(order,approveResponse(i)));
        }
        return list;
    }

    //결제정보
    ApproveResponse approveResponse(int i){
        ApproveResponse approveResponse = new ApproveResponse();
        approveResponse.setApproved_at(LocalDateTime.now().minusDays(i).toString());
        approveResponse.setCreated_at(LocalDateTime.now().minusDays(i).toString());
        approveResponse.setItem_name("itemName");
        approveResponse.setTid("tid");
        return approveResponse;
    }
}
