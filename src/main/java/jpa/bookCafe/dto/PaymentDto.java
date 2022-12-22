package jpa.bookCafe.dto;
import jpa.bookCafe.domain.enumStatus.OrderStatus;
import jpa.bookCafe.domain.enumStatus.PaymentMethod;
import jpa.bookCafe.domain.Payment;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
public class PaymentDto {
    private String tid;
    private String itemName;
    private PaymentMethod paymentMethod;
    private int paymentCost;
    private String paymentTime;
    private Long orderId;
    private OrderStatus status;

    public static List<PaymentDto> createDtos(List<Payment> payments) {

        List<PaymentDto> paymentDtos = new ArrayList<>();
        for (Payment payment : payments) {
            PaymentDto dto = new PaymentDto();
            dto.tid = payment.getTid();
            dto.itemName = payment.getItemName();
            dto.paymentMethod = payment.getPaymentMethod();
            dto.paymentCost = payment.getPaymentCost();
            dto.paymentTime = payment.getPaymentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            dto.orderId = payment.getOrder().getId();
            dto.status = payment.getOrder().getStatus();
            paymentDtos.add(dto);
        }

        return paymentDtos;
    }
}
