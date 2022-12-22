package jpa.bookCafe.dto;

import jpa.bookCafe.domain.enumStatus.MessageType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ChatDto {
    private String writer;
    private String message;
    private String chatRoom;
    private LocalDateTime time;
    private MessageType messageType;
    private Integer currentUserNum;
    private String color;

    @Builder
    public ChatDto(String writer, String message, String chatRoom, LocalDateTime time, MessageType messageType) {
        this.writer = writer;
        this.message = message;
        this.chatRoom = chatRoom;
        this.time = time;
        this.messageType = messageType;
    }


}



