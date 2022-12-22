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
    private final SimpMessageSendingOperations sendingOperations;
    private final ChatSessionRepository chatSessionRepository;


    @MessageMapping("/chat")
    public void chatting(ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        String sessionId = headerAccessor.getSessionId();
        ChatUser user = chatSessionRepository.findName(sessionId);

        chatDto.setTime(LocalDateTime.now());
        chatDto.setColor(user.getColor());

        log.info("msg={}", chatDto);
        sendingOperations.convertAndSend("/topic/"+chatDto.getChatRoom(), chatDto);
    }

    @MessageMapping("/enter")
    public void onConnectEvent(ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        if(chatDto.getWriter() == null) return;
        String sessionId = headerAccessor.getSessionId();
        chatSessionRepository.save(sessionId, chatDto.getWriter());
        int size = chatSessionRepository.size();
        ChatUser user = chatSessionRepository.findName(sessionId);

        headerAccessor.getSessionAttributes().put("roomId",chatDto.getChatRoom());


        chatDto.setCurrentUserNum(size);
        chatDto.setTime(LocalDateTime.now());
        chatDto.setMessage(user.getNickName()+"님이 입장 하셨습니다");
        chatDto.setMessageType(MessageType.ENTER);
        chatDto.setColor(user.getColor());

        log.info("enter accessor={}",headerAccessor.toString());
        log.info("msg={}",chatDto.toString());

        sendingOperations.convertAndSend("/topic/"+chatDto.getChatRoom(), chatDto);
    }

    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String roomId = (String)accessor.getSessionAttributes().get("roomId");
        if(roomId != null){
            String sessionId = accessor.getSessionId();



            ChatUser user = chatSessionRepository.findName(sessionId);
            chatSessionRepository.delete(sessionId);
            int size = chatSessionRepository.size();

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
