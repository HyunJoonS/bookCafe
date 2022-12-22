package jpa.bookCafe.domain;

import jpa.bookCafe.domain.enumStatus.Category;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;
    private String photoPath;
    private int stockQuantity;
    private String deleted;
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Category category;

    public Item(String name, int price, String photoPath, int stockQuantity, Category category) {
        this.name = name;
        this.price = price;
        this.photoPath = photoPath;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.deleted = "N";
    }

    //연관관계 메서드//
    public void setMember(Member member){
        this.member = member;
        member.getItems().add(this);
    }

    public void removeStock(int count){
        int removeStock = stockQuantity - count;
        if(removeStock < 0){
            throw new IllegalStateException("재고 수량 부족");
        }
        stockQuantity = removeStock;
    }

    public void addStock(int count){
        stockQuantity += count;
    }

    public void delete(){
        this.deleted = "Y";
    }
}
