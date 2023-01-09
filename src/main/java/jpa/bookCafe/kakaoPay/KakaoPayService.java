package jpa.bookCafe.kakaoPay;

import jpa.bookCafe.domain.Order;
import jpa.bookCafe.domain.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@Service
public class KakaoPayService {
    String API_KEY = "KakaoAK 903336b4020efe5919c4a0ba729e07d9";
    @Value("${domain}")
    String domain;
    //카카오페이에 결제 요청하기 (redirect로 결제 할수 있는 QR코드 페이지가 넘어옴)
    public ReadyResponse payReady(Order order){

        /**
         * partner_order_id : 결제건에 대한 가맹점의 주문번호
         * partner_user_id : 가맹점에서 사용자를 구분할 수 있는 id
         * item_name : 상품명
         * quantity : 상품 수량
         * total_amount	: 상품 총액
         * tax_free_amount : 상품 비과세 금액
         * */
        String orderId = order.getId().toString();
        String userId = "주문기계1";

        //아이템이 여러개일 때 '아메리카노 외 3건'과 같이 대표이름을 만들어줌
        List<OrderItem> orderItems = order.getOrderItems();
        String itemName = orderItems.get(0).getItem().getName();
        int itemSize = orderItems.size()-1;
        String fullItemName = itemName;

        if(itemSize >0){
            fullItemName = itemName+"외 "+itemSize+"건";
        }

        String totalPrice = String.valueOf(order.getTotalPrice());

        //결제 요청을 보내기 위한 webclient 생성
        WebClient webClient = WebClient
                .builder()
                .baseUrl("https://kapi.kakao.com/v1/payment/ready")
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE +";charset=utf-8");
                    httpHeaders.add("Authorization",API_KEY);
                })
                .build();

        //파라미터 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", orderId);
        params.add("partner_user_id", orderId);
        params.add("item_name", fullItemName);
        params.add("quantity", "1");
        params.add("total_amount", totalPrice);
        params.add("tax_free_amount", "0");
        params.add("approval_url", domain+"/completed");
        params.add("cancel_url", domain+"/cancel");
        params.add("fail_url", domain+"/fail");

        //요청보내고 응답받아오기
        ReadyResponse response = webClient.post()
                .uri(uriBuilder ->
                        uriBuilder.queryParams(params).build()
                ).retrieve()
                .bodyToMono(ReadyResponse.class)
                .block();
        log.info("response={}",response);
        return response;
    }

    //결제 완료하기
    public ApproveResponse payApprove(String tid, String pgToken, String orderId){

        //카카오페이에 결제 완료 확인요청을 하기위한 webClient 생성
        WebClient webClient = WebClient
                .builder()
                .baseUrl("https://kapi.kakao.com/v1/payment/approve")
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE +";charset=utf-8");
                    httpHeaders.add("Authorization",API_KEY);
                })
                .build();

        //넘길 파라미터 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", tid);
        params.add("partner_order_id", orderId); // 주문명
        params.add("partner_user_id", orderId);
        params.add("pg_token", pgToken);

        //요청 보냄
        ApproveResponse response = webClient.post()
                .uri(uriBuilder ->
                        uriBuilder.queryParams(params).build()
                ).retrieve()
                .bodyToMono(ApproveResponse.class)
                .block();

        log.info("response={}",response);
        return response;
    }
}
