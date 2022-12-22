package jpa.bookCafe.controller;

import jpa.bookCafe.domain.Member;
import jpa.bookCafe.dto.MemberDto;
import jpa.bookCafe.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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


    @PostMapping("/login")
    public LoginResponse 로그인(@RequestBody @Validated LoginRequest loginRequest, HttpServletRequest request){
        Member loginMember = memberService.로그인(loginRequest.getLoginId(), loginRequest.getLoginPw());
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        return new LoginResponse(loginMember.getId());
    }

    @PostMapping("/register")
    public CreateMemberResponse 회원가입(@RequestBody @Validated MemberDto memberDto){
        log.info("가입 {}", memberDto.getUserId());
        Long 회원가입 = memberService.회원가입(memberDto);
        return new CreateMemberResponse(회원가입);
    }

    @Data
    private class CreateMemberResponse {
        private Long memberId;

        public CreateMemberResponse(Long memberId) {
            this.memberId = memberId;
        }
    }

    @Data
    private class LoginResponse {
        private Long memberId;

        public LoginResponse(Long memberId) {
            this.memberId = memberId;
        }
    }

    @Data
    private class LoginRequest {
        @NotBlank
        private String loginId;
        @NotBlank
        private String loginPw;
    }
}
