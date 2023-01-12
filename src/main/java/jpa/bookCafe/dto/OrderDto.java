package jpa.bookCafe.dto;

import jpa.bookCafe.domain.Order;
import jpa.bookCafe.domain.OrderItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderDto {
    private String tid;
    private Long orderId;
    private String orderTime;
    private String orderStatus;
    private int orderTotalPrice;
    private List<OrderItemDto> orderItems = new ArrayList<>();

    public static OrderDto createDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(order.getId());
        orderDto.setOrderStatus(order.getStatus().toString());
        orderDto.setOrderTotalPrice(order.getTotalPrice());
         if(order.getPayment() != null){
             orderDto.setOrderTime(order.getPayment().getPaymentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
             orderDto.setTid(order.getPayment().getTid());
         }

        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            orderDto.getOrderItems().add(new OrderItemDto(orderItem));
        }
        return orderDto;
    }
}
