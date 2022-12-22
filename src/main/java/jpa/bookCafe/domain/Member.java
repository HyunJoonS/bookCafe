package jpa.bookCafe.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Member {
    @Id @GeneratedValue
    private Long id;
    private String userId;
    private String password;
    private String shopName;
    private String shopMaster;
    private String tel_number;

    public Member(String userId, String password, String shopName, String shopMaster, String tel_number) {
        this.userId = userId;
        this.password = password;
        this.shopName = shopName;
        this.shopMaster = shopMaster;
        this.tel_number = tel_number;
    }

    @OneToMany(mappedBy = "member")
    private List<Item> items = new ArrayList<>();

}
