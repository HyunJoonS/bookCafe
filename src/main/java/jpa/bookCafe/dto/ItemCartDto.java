package jpa.bookCafe.dto;

import jpa.bookCafe.domain.enumStatus.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCartDto {
    private Long id;
    private String name;
    private Category category;
    private int price;
    private String photoPath;
    private int stockQuantity;
    private int quantity;

    public ItemCartDto(ItemDto itemDto, int quantity) {
        this.id = itemDto.getId();
        this.name = itemDto.getName();
        this.category = itemDto.getCategory();
        this.price = itemDto.getPrice();
        this.photoPath = itemDto.getPhotoPath();
        this.stockQuantity = itemDto.getStockQuantity();
        this.quantity = quantity;
    }
}
