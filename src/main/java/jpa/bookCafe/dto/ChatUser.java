package jpa.bookCafe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatUser {
    private String nickName;
    private String color;
}
