package jpa.bookCafe.domain;

import jpa.bookCafe.domain.enumStatus.MessageType;
import jpa.bookCafe.dto.ChatDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Chat {
    @GeneratedValue
    @Id
    private String id;

    private String writer;
    private String color;
    private String message;
    private String chatRoom;
    private LocalDateTime time;

    @Enumerated(EnumType.STRING)
    private MessageType messageType;
}
