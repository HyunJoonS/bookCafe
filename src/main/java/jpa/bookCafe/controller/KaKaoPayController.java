package jpa.bookCafe.controller;

import jpa.bookCafe.domain.Order;
import jpa.bookCafe.dto.CartDto;
import jpa.bookCafe.dto.ItemCartDto;
import jpa.bookCafe.kakaoPay.ApproveResponse;
import jpa.bookCafe.kakaoPay.KakaoPayService;
import jpa.bookCafe.kakaoPay.ReadyResponse;
import jpa.bookCafe.service.OrderService;
import jpa.bookCafe.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class KaKaoPayController {
    private final KakaoPayService kakaoPayService;
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final SimpMessageSendingOperations sendingOperations;

    @PostMapping("/pay")
    @ResponseBody
    public ReadyResponse Order(HttpServletRequest request,
                               @RequestBody List<ItemCartDto> list){
        List<CartDto> cartDtos = new ArrayList<>();
        for (ItemCartDto itemCartDto : list) {
            cartDtos.add(new CartDto(itemCartDto.getId(), itemCartDto.getQuantity()));
        }

        log.info("cartDto={}", cartDtos);
        Long orderId = orderService.주문하기(cartDtos);
        Order order = orderService.주문조회(orderId);

        ReadyResponse readyResponse = kakaoPayService.payReady(order);

        HttpSession session = request.getSession();
        session.removeAttribute("tid");
        session.removeAttribute("orderId");
        session.setAttribute("orderId",orderId);
        session.setAttribute("tid",readyResponse.getTid());

        log.info("결제대기 tid={}",readyResponse.getTid());
        log.info("결제대기 orderId={}",orderId);
        return readyResponse;
    }

    @GetMapping("/testorder")
    public void testOrder(){
        sendingOperations.convertAndSend("/topic/order","새로운 주문이 있습니다.");
    }

    @GetMapping("/completed")
    public String KakaoPaySuccess(HttpServletRequest request,
                                  @RequestParam("pg_token") String pgToken,
                                  Model model){
        HttpSession session = request.getSession();
        String tid = (String)session.getAttribute("tid");
        Long orderId = (Long)session.getAttribute("orderId");
        String strOrderId = String.valueOf(orderId);
        model.addAttribute("orderId",orderId);
        log.info("결제 tid={}",tid);
        log.info("결제 orderId={}",orderId);
        log.info("결제 pgToken={}",pgToken);
        ApproveResponse approveResponse = kakaoPayService.payApprove(tid, pgToken, strOrderId);



        log.info("approveResponse={}",approveResponse);

        Long 결제생성 = paymentService.결제생성(orderId, approveResponse);
        sendingOperations.convertAndSend("/topic/order","새로운 주문이 있습니다.");
        return "completed";
    }


    @GetMapping("/cancel")
    public String kakaoPayCancel(){
        log.info("cancel");
        return "cancel";
    }

    @GetMapping("/fail")
    public String kakaoPaySuccessFail(Model model){
        model.addAttribute("orderId",1243);
        log.info("fail");
        return "fail";
    }


}
