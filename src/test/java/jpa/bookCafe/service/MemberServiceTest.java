package jpa.bookCafe.service;

import jpa.bookCafe.domain.Item;
import jpa.bookCafe.domain.Member;
import jpa.bookCafe.dto.MemberDto;
import jpa.bookCafe.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {MemberService.class})
class MemberServiceTest {
    @Autowired MemberService memberService;
    @MockBean MemberRepository memberRepository;

    @Test
    void 로그인() {
        Member m = member("hello","spring");
        when(memberRepository.findByUserId("hello")).thenReturn(Optional.of(m));

        Member loginMember = memberService.로그인("hello", "spring");

        assertThat(loginMember).isEqualTo(m);
    }
    @Test
    void 로그인_없는아이디() {
        when(memberRepository.findByUserId("hello")).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> memberService.로그인("hello", "spring"));

        assertThat(ex.getMessage()).isEqualTo("아이디가 존재하지 않습니다.");
    }

    @Test
    void 로그인_비밀번호불일치() {
        Member m = member("hello","spring");
        when(memberRepository.findByUserId("hello")).thenReturn(Optional.of(m));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> memberService.로그인("hello", "fail_pw"));

        assertThat(ex.getMessage()).isEqualTo("비밀번호가 일치하지 않습니다.");
    }

    @Test
    void 회원가입() {
        MemberDto dto = new MemberDto();
        dto.setUserId("hello");
        dto.setPassword("spring");

        when(memberRepository.findByUserId("hello")).thenReturn(Optional.empty());
        when(memberRepository.save(any())).thenAnswer((Answer<Member>) i->{
            Member m = i.getArgument(0);
            m.setId(1L);
            return m;
        });

        Long savedId = memberService.회원가입(dto);
        verify(memberRepository, times(1)).save(any());
        Assertions.assertThat(savedId).isEqualTo(1L);
    }
    @Test
    void 회원가입_중복회원() {
        MemberDto dto = new MemberDto();
        dto.setUserId("hello");
        dto.setPassword("spring");

        Member m = member("hello","spring");
        when(memberRepository.findByUserId("hello")).thenReturn(Optional.of(m));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> memberService.회원가입(dto));

        Assertions.assertThat(true).isEqualTo(ex.getMessage().contains("중복된 회원이 존재합니다."));
    }

    @Test
    void findById() {
        Member m = member("hello1","spring1");
        when(memberRepository.findById(1L)).thenReturn(Optional.of(m));

        Member byId = memberService.findById(1L);

        Assertions.assertThat(byId.getId()).isEqualTo(1L);
    }

    private Member member(String id, String pw) {
        Member member = new Member();
        member.setId(1L);
        member.setUserId(id);
        member.setPassword(pw);
        return member;
    }
}