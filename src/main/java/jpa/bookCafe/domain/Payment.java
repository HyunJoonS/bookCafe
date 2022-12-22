package jpa.bookCafe.domain;

import jpa.bookCafe.domain.enumStatus.PaymentMethod;
import jpa.bookCafe.kakaoPay.ApproveResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id @GeneratedValue
    private Long id;
    private String tid;
    private String itemName;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private int paymentCost;

    @Column(updatable = false)
    private LocalDateTime createTime;
    private LocalDateTime paymentTime;

    @OneToOne(mappedBy = "payment")
    private Order order;


    public static Payment createPayment(Order order, ApproveResponse approveResponse) {
        Payment payment = new Payment();
        payment.setPaymentCost(order.getTotalPrice());
        payment.setItemName(approveResponse.getItem_name());
        payment.setCreateTime(LocalDateTime.parse(approveResponse.getCreated_at()));
        payment.setPaymentTime(LocalDateTime.parse(approveResponse.getApproved_at()));
        payment.setOrder(order);
        payment.setTid(approveResponse.getTid());
        payment.setPaymentMethod(PaymentMethod.MONEY);
        return payment;
    }


    // == 연관관계 메서드 == //
    public void setOrder(Order order){
        order.setPayment(this);
        this.order = order;
    }

}
