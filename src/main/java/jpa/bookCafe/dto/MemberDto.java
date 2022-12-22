package jpa.bookCafe.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class MemberDto {

    @NotBlank
    private String userId;
    @NotBlank
    private String password;
    private String shopName;
    private String shopMaster;
    private String tel_number;

    public MemberDto() {

    }

    public MemberDto(String userId, String password, String shopName, String shopMaster, String tel_number) {
        this.userId = userId;
        this.password = password;
        this.shopName = shopName;
        this.shopMaster = shopMaster;
        this.tel_number = tel_number;
    }
}
