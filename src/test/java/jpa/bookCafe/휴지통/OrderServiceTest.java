package jpa.bookCafe.휴지통;

import jpa.bookCafe.domain.Order;
import jpa.bookCafe.domain.enumStatus.OrderStatus;
import jpa.bookCafe.dto.CartDto;
import jpa.bookCafe.dto.ItemDto;
import jpa.bookCafe.dto.OrderDto;
import jpa.bookCafe.dto.PaymentDto;
import jpa.bookCafe.kakaoPay.ApproveResponse;
import jpa.bookCafe.repository.OrderRepository;
import jpa.bookCafe.service.ItemService;
import jpa.bookCafe.service.OrderService;
import jpa.bookCafe.service.PaymentService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderServiceTest {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    PaymentService paymentService;
    @Autowired
    ItemService itemService;
    @Autowired
    EntityManager em;


    @Test
    public void 주문테스트() throws Exception {
        //Test데이터 셋팅
        ItemDto product1 = new ItemDto("아메리카노", 3000, null, 10);
        ItemDto product2 = new ItemDto("카페라떼", 3500, null, 10);
        ItemDto product3 = new ItemDto("떡볶이", 4000, null, 10);
        ItemDto product4 = new ItemDto("라면", 3500, null, 10);
        Long item1 = itemService.add_item(product1);
        Long item2 = itemService.add_item(product2);
        Long item3 = itemService.add_item(product3);
        Long item4 = itemService.add_item(product4);

        em.flush();
        em.clear();

        //카트 생성
        List<CartDto> cartDtos = new ArrayList<>();
        cartDtos.add(new CartDto(item1, 3));
        cartDtos.add(new CartDto(item2, 2));
        cartDtos.add(new CartDto(item3, 1));

        //주문하기
        Long orderId = orderService.주문하기(cartDtos);

        em.flush();
        em.clear();

        //주문조회
        Order 주문조회 = orderRepository.findById(orderId).get();
        System.out.println("주문조회.getId() = " + 주문조회.getId());

        Assertions.assertThat(주문조회.getOrderItems().size()).isEqualTo(3);
        Assertions.assertThat(주문조회.getStatus()).isEqualTo(OrderStatus.결제대기);

    }

    @Test
    public void 주문조회() throws Exception {
        //Test데이터 셋팅
        ItemDto product1 = new ItemDto("아메리카노", 3000, null, 10);
        ItemDto product2 = new ItemDto("카페라떼", 3500, null, 10);
        ItemDto product3 = new ItemDto("떡볶이", 4000, null, 10);
        ItemDto product4 = new ItemDto("라면", 3500, null, 10);
        Long item1 = itemService.add_item(product1);
        Long item2 = itemService.add_item(product2);
        Long item3 = itemService.add_item(product3);
        Long item4 = itemService.add_item(product4);

        em.flush();
        em.clear();

        //카트 생성
        List<CartDto> cartDtos = new ArrayList<>();
        cartDtos.add(new CartDto(item1, 3));
        cartDtos.add(new CartDto(item2, 2));
        cartDtos.add(new CartDto(item3, 1));

        List<CartDto> cartDtos2 = new ArrayList<>();
        cartDtos2.add(new CartDto(item1, 1));
        cartDtos2.add(new CartDto(item3, 1));
        cartDtos2.add(new CartDto(item4, 1));

        List<CartDto> cartDtos3 = new ArrayList<>();
        cartDtos3.add(new CartDto(item2, 2));
        cartDtos3.add(new CartDto(item3, 2));
        cartDtos3.add(new CartDto(item4, 1));

        //주문하기
        Long orderId1 = orderService.주문하기(cartDtos);
        Long orderId2 = orderService.주문하기(cartDtos2);
        Long orderId3 = orderService.주문하기(cartDtos3);
        Long orderId4 = orderService.주문하기(cartDtos3); // 결제가 없기때문에
        Long orderId5 = orderService.주문하기(cartDtos3); // 검색되지 않아야함



        ApproveResponse approveResponse = new ApproveResponse();
        approveResponse.setCreated_at(LocalDateTime.now().toString());
        approveResponse.setApproved_at(LocalDateTime.now().toString());
        approveResponse.setTid("123123123312");

        paymentService.결제생성(orderId1, approveResponse);
        paymentService.결제생성(orderId2, approveResponse);
        paymentService.결제생성(orderId3, approveResponse);

        em.flush();
        em.clear();

        List<OrderDto> 결제완료주문목록 = orderService.새로운주문조회();
        for (OrderDto order : 결제완료주문목록) {
            System.out.println("order = " + order.getOrderId());
        }

        Assertions.assertThat(결제완료주문목록.size()).isEqualTo(3);
    }

    @Test
    public void 결제조회() throws Exception {
        //Test데이터 셋팅
        ItemDto product1 = new ItemDto("아메리카노", 3000, null, 10);
        ItemDto product2 = new ItemDto("카페라떼", 3500, null, 10);
        ItemDto product3 = new ItemDto("떡볶이", 4000, null, 10);
        ItemDto product4 = new ItemDto("라면", 3500, null, 10);
        Long item1 = itemService.add_item(product1);
        Long item2 = itemService.add_item(product2);
        Long item3 = itemService.add_item(product3);
        Long item4 = itemService.add_item(product4);

        em.flush();
        em.clear();

        //카트 생성
        List<CartDto> cartDtos = new ArrayList<>();
        cartDtos.add(new CartDto(item1, 3));
        cartDtos.add(new CartDto(item2, 2));
        cartDtos.add(new CartDto(item3, 1));

        List<CartDto> cartDtos2 = new ArrayList<>();
        cartDtos2.add(new CartDto(item1, 1));
        cartDtos2.add(new CartDto(item3, 1));
        cartDtos2.add(new CartDto(item4, 1));

        List<CartDto> cartDtos3 = new ArrayList<>();
        cartDtos3.add(new CartDto(item2, 2));
        cartDtos3.add(new CartDto(item3, 2));
        cartDtos3.add(new CartDto(item4, 1));

        //주문하기
        Long orderId1 = orderService.주문하기(cartDtos);
        Long orderId2 = orderService.주문하기(cartDtos2);
        Long orderId3 = orderService.주문하기(cartDtos3);
        Long orderId4 = orderService.주문하기(cartDtos3);
        Long orderId5 = orderService.주문하기(cartDtos3);


        String 하루전 = LocalDateTime.now().minusDays(1).toString();
        String 이틀전 = LocalDateTime.now().minusDays(2).toString();
        String 일주일전 = LocalDateTime.now().minusDays(7).toString();
        String 한달전 = LocalDateTime.now().minusDays(31).toString();

        ApproveResponse approveResponse = new ApproveResponse();
        approveResponse.setCreated_at(하루전);
        approveResponse.setApproved_at(하루전);
        approveResponse.setTid("123123123312");
        approveResponse.setItem_name("기부");

        ApproveResponse approveResponse2 = new ApproveResponse();
        approveResponse2.setCreated_at(이틀전);
        approveResponse2.setApproved_at(이틀전);
        approveResponse2.setTid("123123123312");
        approveResponse2.setItem_name("피자 외 1건");

        ApproveResponse approveResponse3 = new ApproveResponse();
        approveResponse3.setCreated_at(일주일전);
        approveResponse3.setApproved_at(일주일전);
        approveResponse3.setTid("123123123312");
        approveResponse3.setItem_name("치킨");

        ApproveResponse approveResponse4 = new ApproveResponse();
        approveResponse4.setCreated_at(하루전);
        approveResponse4.setApproved_at(하루전);
        approveResponse4.setTid("123123123312");
        approveResponse4.setItem_name("곰탕 외 2건");

        ApproveResponse approveResponse5 = new ApproveResponse();
        approveResponse5.setCreated_at(한달전);
        approveResponse5.setApproved_at(한달전);
        approveResponse5.setTid("123123123312");
        approveResponse5.setItem_name("아메리카노 외 13건");

        paymentService.결제생성(orderId1, approveResponse);
        paymentService.결제생성(orderId2, approveResponse2);
        paymentService.결제생성(orderId3, approveResponse3);
        paymentService.결제생성(orderId4, approveResponse4);
        paymentService.결제생성(orderId5, approveResponse5);

        em.flush();
        em.clear();

        List<PaymentDto> 전체결제정보 = paymentService.전체결제();
        for (PaymentDto dto : 전체결제정보) {
            System.out.println("dto = " + dto);
        }
    }

    @Test
    public void 기간조회() throws Exception {
        //Test데이터 셋팅
        ItemDto product1 = new ItemDto("아메리카노", 3000, null, 10);
        ItemDto product2 = new ItemDto("카페라떼", 3500, null, 10);
        ItemDto product3 = new ItemDto("떡볶이", 4000, null, 10);
        ItemDto product4 = new ItemDto("라면", 3500, null, 10);
        Long item1 = itemService.add_item(product1);
        Long item2 = itemService.add_item(product2);
        Long item3 = itemService.add_item(product3);
        Long item4 = itemService.add_item(product4);

        em.flush();
        em.clear();

        //카트 생성
        List<CartDto> cartDtos = new ArrayList<>();
        cartDtos.add(new CartDto(item1, 3));
        cartDtos.add(new CartDto(item2, 2));
        cartDtos.add(new CartDto(item3, 1));

        List<CartDto> cartDtos2 = new ArrayList<>();
        cartDtos2.add(new CartDto(item1, 1));
        cartDtos2.add(new CartDto(item3, 1));
        cartDtos2.add(new CartDto(item4, 1));

        List<CartDto> cartDtos3 = new ArrayList<>();
        cartDtos3.add(new CartDto(item2, 2));
        cartDtos3.add(new CartDto(item3, 2));
        cartDtos3.add(new CartDto(item4, 1));

        //주문하기
        Long orderId1 = orderService.주문하기(cartDtos);
        Long orderId2 = orderService.주문하기(cartDtos2);
        Long orderId3 = orderService.주문하기(cartDtos3);
        Long orderId4 = orderService.주문하기(cartDtos3);
        Long orderId5 = orderService.주문하기(cartDtos3);

        String 오늘 = LocalDateTime.now().toString();
        String 하루전 = LocalDateTime.now().minusDays(1).toString();
        String 이틀전 = LocalDateTime.now().minusDays(2).toString();
        String 일주일전 = LocalDateTime.now().minusDays(7).toString();
        String 한달전 = LocalDateTime.now().minusDays(31).toString();

        ApproveResponse approveResponse = new ApproveResponse();
        approveResponse.setCreated_at(오늘);
        approveResponse.setApproved_at(오늘);
        approveResponse.setTid("123123123312");
        approveResponse.setItem_name("기부");

        ApproveResponse approveResponse2 = new ApproveResponse();
        approveResponse2.setCreated_at(이틀전);
        approveResponse2.setApproved_at(이틀전);
        approveResponse2.setTid("123123123312");
        approveResponse2.setItem_name("피자 외 1건");

        ApproveResponse approveResponse3 = new ApproveResponse();
        approveResponse3.setCreated_at(일주일전);
        approveResponse3.setApproved_at(일주일전);
        approveResponse3.setTid("123123123312");
        approveResponse3.setItem_name("치킨");

        ApproveResponse approveResponse4 = new ApproveResponse();
        approveResponse4.setCreated_at(하루전);
        approveResponse4.setApproved_at(하루전);
        approveResponse4.setTid("123123123312");
        approveResponse4.setItem_name("곰탕 외 2건");

        ApproveResponse approveResponse5 = new ApproveResponse();
        approveResponse5.setCreated_at(한달전);
        approveResponse5.setApproved_at(한달전);
        approveResponse5.setTid("123123123312");
        approveResponse5.setItem_name("아메리카노 외 13건");

        paymentService.결제생성(orderId1, approveResponse);
        paymentService.결제생성(orderId2, approveResponse2);
        paymentService.결제생성(orderId3, approveResponse3);
        paymentService.결제생성(orderId4, approveResponse4);
        paymentService.결제생성(orderId5, approveResponse5);

        em.flush();
        em.clear();

        List<PaymentDto> 조회dto = paymentService.기간조회(1);
        for (PaymentDto dto : 조회dto) {
            System.out.println("dto = " + dto);
        }
        Assertions.assertThat(조회dto.size()).isEqualTo(4);
    }

    @Test
    public void OrderServiceTest() throws Exception{
        //given

        //when

        //then
    }
}