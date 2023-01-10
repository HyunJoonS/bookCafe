package jpa.bookCafe.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDto {
    private String loginId;
    private String loginPw;
}
