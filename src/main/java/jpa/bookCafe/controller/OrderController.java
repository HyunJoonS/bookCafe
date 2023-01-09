package jpa.bookCafe.controller;

import jpa.bookCafe.dto.OrderDto;
import jpa.bookCafe.dto.PaymentDto;
import jpa.bookCafe.service.OrderService;
import jpa.bookCafe.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final PaymentService paymentService;

    //전체 주문조회
    @GetMapping("/api/orders")
    public List<OrderDto> orderList(){
        return orderService.새로운주문조회();
    }

    //단건 주문조회
    @GetMapping("/api/order")
    public OrderDto findOrder(@RequestParam("orderId") Long orderId){
        log.info("orderId:",orderId) ;
        return orderService.findByIdDto(orderId);
    }

    //전체 결제정보 조회
    @GetMapping("/api/payment")
    public List<PaymentDto> paymentDtos(@RequestParam(value = "day",required = false) Integer beforeDay){
        return paymentService.기간조회(beforeDay);
    }

    //주문 상태 변경
    @PostMapping("/api/order")
    public Long orderUpdate(@RequestBody OrderDto orderDto){
        return orderService.statusUpdate(orderDto);
    }
}
