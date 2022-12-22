//package jpa.bookCafe.service;
//
//import jpa.bookCafe.domain.Member;
//import jpa.bookCafe.domain.SeatTable;
//import jpa.bookCafe.dto.MemberDto;
//import jpa.bookCafe.repository.MemberRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import java.util.List;
//
//@SpringBootTest
//@Transactional
//public class SeatTableServiceTest {
//
//
//    @Autowired private MemberService memberService;
//    @Autowired private ItemService itemService;
//    @Autowired private MemberRepository memberRepository;
//    @Autowired private SeatTableService seatTableService;
//    @Autowired EntityManager em;
//
//    @BeforeEach
//    public void before(){
//        for (int i = 1; i < 4; i++) {
//            MemberDto memberDto = new MemberDto("매장"+i, "123123", "test"+i,
//                    "가게"+i,"010-123-121"+i);
//            memberService.회원가입(memberDto);
//        }
//        System.out.println("회원가입 완료");
//    }
//
//    @Test
//    public void 테이블생성() throws Exception{
//        //given
//        Member user1 = memberRepository.findByUserId("매장1").get();
//        Long tb1_id = seatTableService.테이블추가(1, user1.getId());
//        Long tb2_id = seatTableService.테이블추가(2, user1.getId());
//
//        Member user2 = memberRepository.findByUserId("매장2").get();
//        seatTableService.테이블추가(1, user2.getId());
//        seatTableService.테이블추가(2, user2.getId());
//
//        em.flush();
//        em.clear();
//        //when
//        List<SeatTable> 테이블목록 = seatTableService.테이블목록(user1.getId());
//        SeatTable table1 = seatTableService.findOne(tb1_id);
//
//        //then
//        Assertions.assertThat(테이블목록.size()).isEqualTo(2);
//        Assertions.assertThat(table1.getCart().getId()).isNotNull();
//
//    }
//
//    @Test
//    public void 중복확인() throws Exception{
//        //given
//        Member user1 = memberRepository.findByUserId("매장1").get();
//        Long tb1_id = seatTableService.테이블추가(1, user1.getId());
//
//        IllegalStateException aThrows = org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class, () -> {
//            seatTableService.테이블추가(1, user1.getId());
//        });
//
//        Assertions.assertThat(aThrows.getMessage()).isEqualTo("이미 존재하는 테이블번호 입니다.");
//    }
//}
