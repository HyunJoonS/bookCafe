package jpa.bookCafe.kakaoPay;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Amount { //카카오페이에서 결제완료시 데이터를 받아올 때 사용할 dto
    private int total;
    private int tax_free;
    private int vat;
    private int point;
    private int discount;
}