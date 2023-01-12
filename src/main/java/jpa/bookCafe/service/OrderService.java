package jpa.bookCafe.service;

import jpa.bookCafe.domain.enumStatus.OrderStatus;
import jpa.bookCafe.domain.Item;
import jpa.bookCafe.domain.Order;
import jpa.bookCafe.domain.OrderItem;
import jpa.bookCafe.dto.CartDto;
import jpa.bookCafe.dto.OrderDto;
import jpa.bookCafe.repository.ItemRepository;
import jpa.bookCafe.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
private final ItemRepository itemRepository;
private final OrderRepository orderRepository;

    //order 생성, 장바구니를 받아옴
    public Long 주문하기(List<CartDto> cartItems){

        Order order = new Order();
        order.setStatus(OrderStatus.결제대기);
        for (CartDto cartItem : cartItems) {
            Item item = itemRepository.findById(cartItem.getItemId()).get();
            OrderItem orderItem = OrderItem.createOrderItem(item, cartItem.getQuantity());
            order.addOrderItem(orderItem);
        }

        Order save = orderRepository.save(order);
        System.out.println("save.getId() = " + save.getId());
        System.out.println("order.getId() = " + order.getId());
        return order.getId();
    }

    //단건 조회
    public Order 주문조회(Long orderId) {
        return orderRepository.findById(orderId).get();
    }

    //dto로 조회
    public OrderDto findByIdDto(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        return OrderDto.createDto(order);
    }

    //결제건만 조회
    public List<OrderDto> 새로운주문조회( ) {
        List<Order> orders = orderRepository.findByAll();

        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : orders) {
           orderDtos.add(OrderDto.createDto(order));
        }
        return orderDtos;
    }

    //상태 변경 결제대기 -> 결제완료 -> 조리중 -> 조리완료
    public Long statusUpdate(OrderDto orderDto){
        Order order = orderRepository.findById(orderDto.getOrderId()).get();
        order.setStatus(OrderStatus.valueOf(orderDto.getOrderStatus()));
        return order.getId();
    }
}
