package jpa.bookCafe.controller;

import jpa.bookCafe.domain.enumStatus.MessageType;
import jpa.bookCafe.dto.ChatDto;
import jpa.bookCafe.repository.ChatSessionRepository;
import jpa.bookCafe.dto.ChatUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


import java.time.LocalDateTime;

@Controller
@Slf4j
@RequiredArgsConstructor
public class StompController {
    private final SimpMessageSendingOperations sendingOperations; //메세지 발송
    private final ChatSessionRepository chatSessionRepository; //현재 접속 유저 목록

    // app/chat 경로로 메세지가 들어오면
    @MessageMapping("/chat")
    public void chatting(ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) throws Exception {

        //어떤 유저인지 구분하기 위하여 세션id 사용. (본 프로젝트는 로그인기능이 없기때문에 클라이언트 별로 유저를 구분)
        String sessionId = headerAccessor.getSessionId();
        ChatUser user = chatSessionRepository.findById(sessionId);

        chatDto.setTime(LocalDateTime.now());
        chatDto.setColor(user.getColor()); //유저아이디에 적용될 색깔

        log.info("msg={}", chatDto);
        sendingOperations.convertAndSend("/topic/"+chatDto.getChatRoom(), chatDto); // /topic/채팅방에 메세지전파
    }

    //~~가 입장 하였습니다. 시스템메세지 발송
    @MessageMapping("/enter")
    public void onConnectEvent(ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        if(chatDto.getWriter() == null) return;

        String sessionId = headerAccessor.getSessionId();
        chatSessionRepository.save(sessionId, chatDto.getWriter()); //현재 접속중인 유저목록에 추가
        int size = chatSessionRepository.size();
        ChatUser user = chatSessionRepository.findById(sessionId); //유저 생성

        headerAccessor.getSessionAttributes().put("roomId",chatDto.getChatRoom()); //시스템 메세지를 보낼 방 가져오기

        //메세지 정보 생성
        chatDto.setCurrentUserNum(size);
        chatDto.setTime(LocalDateTime.now());
        chatDto.setMessage(user.getNickName()+"님이 입장 하셨습니다");
        chatDto.setMessageType(MessageType.ENTER);
        chatDto.setColor(user.getColor());

        log.info("enter accessor={}",headerAccessor.toString());
        log.info("msg={}",chatDto.toString());

        //발송
        sendingOperations.convertAndSend("/topic/"+chatDto.getChatRoom(), chatDto);
    }

    //퇴장, 연결이 끊어졌을 때
    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String roomId = (String)accessor.getSessionAttributes().get("roomId"); //방송할 채팅방 이름
        if(roomId != null){
            String sessionId = accessor.getSessionId();

            ChatUser user = chatSessionRepository.findById(sessionId); //등록된 어떤 유저인지
            chatSessionRepository.delete(sessionId); //현재 접속중인 유저 목록에서 제거
            int size = chatSessionRepository.size();

            //메세지 생성 및 발송
            ChatDto chatDto = new ChatDto();
            chatDto.setCurrentUserNum(size);
            chatDto.setTime(LocalDateTime.now());
            chatDto.setMessage(user.getNickName()+"님이 퇴장 하셨습니다");
            chatDto.setMessageType(MessageType.QUIT);
            chatDto.setChatRoom(roomId);

            log.info("quit event={}",accessor);

            sendingOperations.convertAndSend("/topic/"+chatDto.getChatRoom(), chatDto);
        }
    }
}
