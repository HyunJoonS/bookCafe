package jpa.bookCafe.service;

import jpa.bookCafe.Aes256;
import jpa.bookCafe.domain.Item;
import jpa.bookCafe.dto.ItemDto;
import jpa.bookCafe.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;
    private final Aes256 aes256;

    //전체 메뉴
    public List<ItemDto> findAll_ItemDtos(){
        List<ItemDto> itemDto = itemRepository.findItemDto();
        return itemDto;
    }

    //메뉴 추가
    public Long add_item(ItemDto dto) throws Exception {
        Item item = new Item(dto.getName(), dto.getPrice(), dto.getPhotoPath(), dto.getStockQuantity(), dto.getCategory());
        if(dto.getPassword() != null){
            String encrypt = aes256.Encrypt(dto.getPassword());
            item.setPassword(encrypt);
        }
        Item saved = itemRepository.save(item);
        return saved.getId();
    }

    //메뉴 수정
    public Long update_Item(Long itemId, ItemDto dto) throws Exception {
        passwordAuth(itemId,dto.getPassword());

        Item findItem = itemRepository.findById(itemId).get();
        findItem.setName(dto.getName());
        findItem.setPrice(dto.getPrice());
        findItem.setPhotoPath(dto.getPhotoPath());

        return findItem.getId();
    }

    //메뉴 단건 조회
    public Item findItem(Long ItemId){
        return itemRepository.findById(ItemId).get();
    }

    //메뉴 삭제
    public void delete(Long itemId,String password) throws Exception {
        passwordAuth(itemId,password);
        Item item = itemRepository.findById(itemId).get();
        item.delete();
    }


    //삭제나 수정시 암호인증
    public void passwordAuth(Long itemId, String password) throws Exception {
        Item item = itemRepository.findById(itemId).get();
        if(password.equals("admin1")){
            return;
        }
        else{
            String encrypt = aes256.Encrypt(password);
            if(item.getPassword() == null || !item.getPassword().equals(encrypt)){
                throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
            }
        }
    }
}
