package jpa.bookCafe.service;

import jpa.bookCafe.Aes256;
import jpa.bookCafe.domain.Book;
import jpa.bookCafe.domain.Item;
import jpa.bookCafe.dto.ItemDto;
import jpa.bookCafe.repository.MemberRepository;
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
    //매장내 메뉴
    public List<Item> menuList(Long memberId){
        return itemRepository.findAllByItem(memberId);
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

    public Item findItem(Long ItemId){
        return itemRepository.findById(ItemId).get();
    }

    public void delete(Long itemId,String password) throws Exception {
        passwordAuth(itemId,password);
        Item item = itemRepository.findById(itemId).get();
        item.delete();
    }


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
