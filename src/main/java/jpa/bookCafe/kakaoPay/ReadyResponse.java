package jpa.bookCafe.kakaoPay;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReadyResponse { //카카오페이에서 결제요청시 응답받기 위한 dto

    private String tid;
    private String next_redirect_pc_url;
    private String partner_order_id;
}