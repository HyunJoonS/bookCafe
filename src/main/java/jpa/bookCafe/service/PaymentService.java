package jpa.bookCafe.service;

import jpa.bookCafe.domain.enumStatus.OrderStatus;
import jpa.bookCafe.domain.Order;
import jpa.bookCafe.domain.Payment;
import jpa.bookCafe.dto.PaymentDto;
import jpa.bookCafe.kakaoPay.ApproveResponse;
import jpa.bookCafe.repository.OrderRepository;
import jpa.bookCafe.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public Long 결제생성(Long orderId, ApproveResponse approveResponse) {

        Order order = orderRepository.findById(orderId).get();
        //결제 생성
        Payment payment = Payment.createPayment(order, approveResponse);
        order.setStatus(OrderStatus.결제완료);
        Payment savedPayment = paymentRepository.save(payment);
        orderRepository.save(order);
        return savedPayment.getId();
    }

    public List<PaymentDto> 전체결제(){
        List<Payment> all = paymentRepository.findAll();
        List<PaymentDto> dtos = PaymentDto.createDtos(all);
        return dtos;
    }
    public List<PaymentDto> 기간조회(Integer beforeDays){
        List<Payment> all;
        if(beforeDays!=null){
            LocalDateTime start = LocalDateTime.of(LocalDate.now().minusDays(beforeDays), LocalTime.of(0,0,0));
            LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));
            all = paymentRepository.findAllBetweenDays(start,end);
        }else {
            all = paymentRepository.findAll();
        }
        List<Payment> collect = all.stream().sorted(Comparator.comparing(Payment::getPaymentTime).reversed()).collect(Collectors.toList());


        return PaymentDto.createDtos(collect);
    }
}
