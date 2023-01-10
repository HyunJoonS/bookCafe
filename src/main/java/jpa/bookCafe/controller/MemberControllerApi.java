package jpa.bookCafe.controller;

import jpa.bookCafe.domain.Member;
import jpa.bookCafe.dto.LoginDto;
import jpa.bookCafe.dto.MemberDto;
import jpa.bookCafe.service.MemberService;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberControllerApi {
    private final MemberService memberService;

    @GetMapping("/test")
    public String Test(){
        String API_ID = "tJonrAzZb7_ojoWfWQBa";
        String API_KEY = "_dgE8mr70g";
        String query = "9791136299345";
        WebClient webClient = WebClient
                .builder()
                .baseUrl("https://openapi.naver.com/v1/search/book.json")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add("X-Naver-Client-Id",API_ID);
                    httpHeaders.add("X-Naver-Client-Secret",API_KEY);
                })
                .build();

        String response = webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.queryParam("query", query).build()
                ).retrieve().bodyToMono(String.class).block();
        return response;
    }


    @PostMapping("api/login")
    public LoginResponse 로그인(@RequestBody @Validated LoginDto loginRequest, HttpServletRequest request){

        log.info("{},{}",loginRequest.getLoginId(),loginRequest.getLoginPw());

        Member loginMember = memberService.로그인(loginRequest.getLoginId(), loginRequest.getLoginPw());
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        return new LoginResponse(loginMember.getId());
    }

     @PostMapping("api/register")
    public CreateMemberResponse 회원가입(@RequestBody @Validated MemberDto memberDto){
        log.info("가입 {}", memberDto.getUserId());
        Long 회원가입 = memberService.회원가입(memberDto);
        return new CreateMemberResponse(회원가입);
    }
    @GetMapping("api/admin")
    public String 로그인여부(){
        return "ok";
    }
    @GetMapping("api/logout")
    public String 로그아웃(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "ok";
    }

    @Data
    private static class CreateMemberResponse {
        private Long memberId;
        public CreateMemberResponse(Long memberId) {
            this.memberId = memberId;
        }
    }

    @Data
    private static class LoginResponse {
        private Long memberId;
        public LoginResponse(Long memberId) {
            this.memberId = memberId;
        }
    }
}
