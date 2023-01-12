package jpa.bookCafe.repository;

import jpa.bookCafe.domain.Item;
import jpa.bookCafe.dto.ItemDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
class ItemRepositoryTest {
    @Autowired ItemRepository itemRepository;

    @BeforeEach
    void setup(){
        itemRepository.save(new Item("아메리카노1", 123, "", 10, null));
        itemRepository.save(new Item("아메리카노2", 1234, "", 10, null));
        itemRepository.save(new Item("아메리카노3", 1235, "", 10, null));
    }

    @DisplayName("전체조회")
    @Test
    void findItemDto() {
        //when
        List<ItemDto> itemDto = itemRepository.findItemDto();

        //then
        Assertions.assertThat(itemDto.size()).isEqualTo(3);
    }

    @DisplayName("id로 여러개 찾기")
    @Test
    void findItemDtoByIds() {
        //given
        Long[] longs = {1L, 2L, 3L};

        //when
        List<ItemDto> itemDto = itemRepository.findItemDtoByIds(longs);

        //then
        Assertions.assertThat(itemDto.size()).isEqualTo(3);
    }
}