package jpa.bookCafe.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    private Long id;

    private int orderPrice;
    private int orderCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;


    //== 비즈니스 메서드 ==//
    public static OrderItem createOrderItem(Item item, int orderCount){
        OrderItem orderDetail = new OrderItem();
        orderDetail.setItem(item);
        orderDetail.orderCount = orderCount;
        orderDetail.orderPrice = orderCount * item.getPrice();
        item.removeStock(orderCount);
        return orderDetail;
    }

    //== 연관관계 메서드 ==//
    public void setItem(Item item){
        item.getOrderItems().add(this);
        this.item = item;
    }

    public void cancel(){
        item.addStock(orderCount);
    }

}
