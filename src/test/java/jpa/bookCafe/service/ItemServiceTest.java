package jpa.bookCafe.service;

import jpa.bookCafe.Aes256;
import jpa.bookCafe.domain.Item;
import jpa.bookCafe.dto.ItemDto;
import jpa.bookCafe.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {ItemService.class})
class ItemServiceTest {
    @Autowired ItemService itemService;
    @MockBean ItemRepository itemRepository;
    @SpyBean Aes256 aes256;

    @Test
    public void ItemServiceTest() throws Exception{
        //given
        when(itemRepository.findItemDto()).thenReturn(itemList());

        //when
        List<ItemDto> all_itemDtos = itemService.findAll_ItemDtos();

        //then
        Assertions.assertThat(all_itemDtos.size()).isEqualTo(10);
    }

    private List<ItemDto> itemList() {
        List<ItemDto> list = new ArrayList<>();
        for(int i=0; i<10; i++) {
            list.add(new ItemDto());
        }
        return list;
    }

    @Test
    void add_item() throws Exception {
        when(itemRepository.save(any(Item.class))).thenAnswer((Answer<Item>) i->{
            Item item = i.getArgument(0);
            item.setId(1L);
            return item;
        });

        Long savedId = itemService.add_item(new ItemDto());

        Assertions.assertThat(savedId).isEqualTo(1L);
        verify(itemRepository,times(1)).save(any());
    }


    @Test
    void delete() throws Exception {
        Item item = new Item("아메리카노", 123, "", 1, null);
        item.setPassword(aes256.Encrypt("1234"));
        item.setId(1L);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        itemService.delete(1L,"1234");

        Assertions.assertThat(item.getDeleted()).isEqualTo("Y");
    }
}