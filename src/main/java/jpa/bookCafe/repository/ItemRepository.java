package jpa.bookCafe.repository;

import jpa.bookCafe.domain.Item;
import jpa.bookCafe.dto.ItemDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
//    @Query("select i from Item i join fetch i.member")
//    List<Item> findAllByItem(@Param("memberId") Long memberId);

    @Query("select new jpa.bookCafe.dto.ItemDto(i.name, i.price, i.photoPath, i.stockQuantity, i.category, i.id) from Item i where i.deleted='N'")
    List<ItemDto> findItemDto();

    @Query("select new jpa.bookCafe.dto.ItemDto(i.name, i.price, i.photoPath, i.stockQuantity, i.category, i.id) from Item i where i.id in (:itemIds)")
    List<ItemDto> findItemDtoByIds(@Param("itemIds")Long[] itemIds);
}
