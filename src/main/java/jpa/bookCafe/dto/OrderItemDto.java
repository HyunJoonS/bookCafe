package jpa.bookCafe.dto;

import jpa.bookCafe.domain.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private Long itemCode;
    private String itemName;
    private int itemPrice;
    private int count;

    public OrderItemDto(OrderItem orderItem) {
        this.itemCode = orderItem.getItem().getId();
        this.itemName = orderItem.getItem().getName();
        this.itemPrice = orderItem.getItem().getPrice();
        this.count = orderItem.getOrderCount();
    }
}
