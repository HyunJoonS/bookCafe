package jpa.bookCafe.service;

import jpa.bookCafe.domain.Item;
import jpa.bookCafe.domain.Order;
import jpa.bookCafe.domain.Payment;
import jpa.bookCafe.domain.enumStatus.Category;
import jpa.bookCafe.domain.enumStatus.OrderStatus;
import jpa.bookCafe.dto.CartDto;
import jpa.bookCafe.dto.OrderDto;
import jpa.bookCafe.kakaoPay.ApproveResponse;
import jpa.bookCafe.repository.ItemRepository;
import jpa.bookCafe.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {OrderService.class})
public class OrderServiceTest {
    @MockBean
    ItemRepository itemRepository;
    @MockBean
    OrderRepository orderRepository;

    @Autowired OrderService orderService;

    @Test
    void 주문하기() {
        //given
        when(itemRepository.findById(any(Long.class))).thenAnswer((Answer<Optional>) i->{
            Long id = i.getArgument(0);
            return Optional.ofNullable(item(id));
        });
        when(orderRepository.save(any(Order.class))).thenAnswer((Answer<Order>) i->{
            Order order = i.getArgument(0);
            order.setId(2L);
            return order;
        });
        List<CartDto> cartDtos = 장바구니();

        //when
        Long orderId = orderService.주문하기(cartDtos);

        //then
        assertThat(orderId).isEqualTo(2L);
    }

    @DisplayName("id를 조회하면 Dto로 반환이 된다")
    @Test
    void findByIdDto() {
        //given
        Order order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.결제대기);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        //when
        OrderDto dto = orderService.findByIdDto(1L);

        //then
        assertThat(dto.getOrderId()).isEqualTo(1L);
        assertThat(dto.getOrderStatus()).isEqualTo(OrderStatus.결제대기.toString());
    }

    @DisplayName("전체 조회 Payment 테이블 Join.")
    @Test
    void 새로운주문조회() {
        //given
        List<Order> orders = new ArrayList<>();
        orders.add(createOrder(1L, OrderStatus.결제완료));
        orders.add(createOrder(2L, OrderStatus.결제완료));
        orders.add(createOrder(3L, OrderStatus.결제완료));
        when(orderRepository.findByAll()).thenReturn(orders);


        //when
        List<OrderDto> dtos = orderService.새로운주문조회();

        //then
        assertThat(dtos.size()).isEqualTo(3);
    }

    @DisplayName("주문상태 변경")
    @Test
    void statusUpdate() {
        //given
        Order order = createOrder(1L, OrderStatus.결제대기);
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(1L);
        orderDto.setOrderStatus("결제완료");
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        //when
        Long id = orderService.statusUpdate(orderDto);

        //then
        assertThat(id).isEqualTo(1L);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.결제완료); //상태 업데이트 확인
    }

    //주문생성
    private Order createOrder(Long l, OrderStatus status) {
        Order order = new Order();
        order.setId(l);
        order.setStatus(status);
        Payment.createPayment(order, approveResponse());
        return order;
    }

    //카트 생성
    List<CartDto> 장바구니() {
        List<CartDto> cartDtos = new ArrayList<>();
        cartDtos.add(new CartDto(1L, 3));
        cartDtos.add(new CartDto(2L, 2));
        cartDtos.add(new CartDto(3L, 1));
        return cartDtos;
    }

    //아이템생성
    Item item(long i){
        Item item = new Item("메뉴"+i, 3000, null, 10, Category.DRINK);
        item.setId(i);
        return item;
    }

    //결제정보
    ApproveResponse approveResponse(){
        ApproveResponse approveResponse = new ApproveResponse();
        approveResponse.setApproved_at(LocalDateTime.now().toString());
        approveResponse.setCreated_at(LocalDateTime.now().toString());
        approveResponse.setItem_name("itemName");
        approveResponse.setTid("tid");
        return approveResponse;
    }
}

