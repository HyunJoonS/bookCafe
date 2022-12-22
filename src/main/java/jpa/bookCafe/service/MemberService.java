package jpa.bookCafe.service;

import jpa.bookCafe.domain.Member;
import jpa.bookCafe.dto.MemberDto;
import jpa.bookCafe.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
//@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public Member 로그인(String loginId, String password ){
        Member member = memberRepository.findByUserId(loginId)
                .orElseThrow(()-> {throw new IllegalStateException("아이디가 존재하지 않습니다.");});

        if(member.getPassword() != password){
            throw new IllegalStateException("패스워드 불일치");
        }

        return member;
    }

    public Long 회원가입(MemberDto dto) {
        this.memberRepository.findByUserId(dto.getUserId()).ifPresent(s -> {
            throw new IllegalStateException("중복된 회원이 존재합니다." + s.getUserId());
        });
        Member member = new Member(dto.getUserId(), dto.getPassword(), dto.getShopName(), dto.getShopMaster(), dto.getTel_number());
        memberRepository.save(member);
        return member.getId();
    }

    public Member findById(Long id){
        return memberRepository.findById(id).get();
    }
}
