package jpa.bookCafe.dto;

import jpa.bookCafe.domain.enumStatus.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private Long id;
    private String name;
    private Category category;
    private int price;
    private String photoPath;
    private int stockQuantity;
    private String password;

    public ItemDto(String name, int price, String photoPath, int stockQuantity, Category category, Long id) {
        this.name = name;
        this.price = price;
        this.photoPath = photoPath;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.id = id;
    }

    public ItemDto(String name, int price, String photoPath, int stockQuantity, Category category) {
        this.name = name;
        this.price = price;
        this.photoPath = photoPath;
        this.stockQuantity = stockQuantity;
        this.category = category;
    }

    public ItemDto(String name, int price, String photoPath, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.photoPath = photoPath;
        this.stockQuantity = stockQuantity;
    }
}
