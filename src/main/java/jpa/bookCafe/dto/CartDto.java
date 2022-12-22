package jpa.bookCafe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartDto {
    private Long itemId;
    private int quantity;

    /**
      * @param itemId
     * @param quantity
     */
    public CartDto(Long itemId, int quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }
}
