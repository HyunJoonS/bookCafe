package jpa.bookCafe.domain;

import jpa.bookCafe.domain.enumStatus.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "orders")
public class Order {

    @Id @GeneratedValue
    private Long id;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    //== 비즈니스 메서드 ==//
    public static Order createOrder(List<OrderItem> orderItemLists){
        Order order = new Order();
        for (OrderItem orderItem : orderItemLists) {
            order.addOrderItem(orderItem);
        }
        order.status = OrderStatus.결제대기;
        return order;
    }

    public void cancel(){
        if(!status.equals(OrderStatus.조리중)){
            for (OrderItem orderItem : orderItems) {
                orderItem.cancel();
                this.status = OrderStatus.주문취소;
            }
        }else {
            throw new IllegalStateException("현재 주문 취소가 불가능한 상태입니다.");
        }
    }
    public int getTotalPrice(){
        int sum=0;
        for (OrderItem orderItem : orderItems) {
            sum+=orderItem.getOrderPrice();
        }
        return sum;
    }

    //== 연관관계 메서드 ==//
    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
}
