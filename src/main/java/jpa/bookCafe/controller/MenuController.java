package jpa.bookCafe.controller;

import jpa.bookCafe.dto.CartDto;
import jpa.bookCafe.dto.ItemCartDto;
import jpa.bookCafe.dto.ItemDto;
import jpa.bookCafe.file.FileStore;
import jpa.bookCafe.repository.ItemRepository;
import jpa.bookCafe.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MenuController {
    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final FileStore fileStore;

    @Value("${file.dir}")
    String fileDir;

    //메뉴 수정
    @PostMapping("/api/menu")
    public String MenuAdd(@RequestPart MultipartFile file,
                          @RequestPart("data") ItemDto itemDto) throws Exception {
        log.info("file {}", file);
        log.info("item {}", itemDto);
        if (!file.isEmpty()) {
            String storeFile = fileStore.storeFile(file); //파일 저장후 경로 반환
            itemDto.setPhotoPath(storeFile);
            itemService.add_item(itemDto); //메뉴 저장
        }
        return "ok";
    }

    //삭제
    @DeleteMapping("/api/menu")
    public String MenuDelete(@RequestParam("id") Long id, @RequestParam("password") String pw) throws Exception {
        log.info("id={}, pw={}", id, pw);
        itemService.delete(id, pw);
        return "ok";
    }

    //전체 조회
    @GetMapping("/api/menu")
    public List<ItemDto> MenuList() {
        List<ItemDto> itemDto = itemRepository.findItemDto();
        log.info("{}", itemDto);
        return itemDto;
    }

    //이미지 조회
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        // UrlResource로 파일을 읽어서 @ResponseBody로 이미지 바이너리를 반환한다.
        // fullPath로 전체 경로 /Users/... 를 가져온다.
        return new UrlResource("file:" + fileDir + filename);
    }

    //장바구니에 상품 추가
    @PostMapping("/api/menu/cart")
    public String menuInCart(HttpServletRequest request,
                             CartDto cartDto) {

        log.info("넘어온값 = {}", cartDto);
        HttpSession session = request.getSession(); //장바구니는 세션마다 초기화

        List<CartDto> attribute = (List<CartDto>) session.getAttribute(SessionConst.CART); //기존 장바구니 불러옴
        log.info("att={}, sessionID={}", attribute, session.getId());

        if (attribute != null) {
            long count = attribute.stream().filter(dto -> dto.getItemId() == cartDto.getItemId()).count(); //중복 방지
            log.info("count = {}");
            if (count > 0) {
                return "이미 장바구니에 추가된 상품입니다.";
            }
            attribute.add(cartDto); //정상 추가
        } else {
            List<CartDto> list = new ArrayList<>(); //처음 장바구니에 넣는경우 새로 장바구니 카트를 생성
            list.add(cartDto);
            session.setAttribute(SessionConst.CART, list);
            log.info("list = {}", list);
        }
        return "장바구니에 추가되었습니다.";

    }

    //장바구니 조회
    @GetMapping("/api/menu/cart")
    public List<ItemCartDto> cartList(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        //세션 단위 장바구니
        if (session != null) {

            //장바구니 조회 -> 아직 dto로 상품id, 갯수만 들어있음
            List<CartDto> attribute = (List<CartDto>) session.getAttribute(SessionConst.CART);
            log.info("attr={}", attribute);
            if (attribute != null) {

                //상품정보를 보여주기위해 상품정보를 조회해야함
                Long[] itemIds = new Long[attribute.size()];
                for(int i=0; i< attribute.size(); i++){
                    CartDto dto = attribute.get(i);
                    log.info("dto={}", dto);
                    itemIds[i] = dto.getItemId();
                }

                //가져온 아이디로 리스트 검색 -> sql in절 사용 다중 조회
                List<ItemDto> itemDtoByIds = itemRepository.findItemDtoByIds(itemIds);
                log.info("itemdtos~ = {}", itemDtoByIds);

                //카트 순서와 꺼내온 itemdto 순서를 정리시키고
                List<CartDto> sortedAttribute = attribute.stream().sorted(Comparator.comparing((CartDto c) -> c.getItemId())).collect(Collectors.toList());
                log.info("sorted attribute={}", sortedAttribute);
                List<ItemDto> sortedItemDtos = itemDtoByIds.stream().sorted(Comparator.comparing((ItemDto i) -> i.getId())).collect(Collectors.toList());
                log.info("sorted sortedItemDtos={}", sortedItemDtos);

                //장바구니 목록 반환
                List<ItemCartDto> result = new ArrayList<>();
                for (int i = 0; i < sortedItemDtos.stream().count(); i++) {
                    ItemDto itemDto = sortedItemDtos.get(i);
                    CartDto cartDto = sortedAttribute.get(i);
                    result.add(new ItemCartDto(itemDto, cartDto.getQuantity()));
                }

                return result;
            }
        }
        return null;
    }

    //장바구니 비우기
    @DeleteMapping("/api/menu/cart")
    public String cartClear(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            session.removeAttribute(SessionConst.CART);
        }
        return "장바구니를 비웠습니다.";
    }

    //장바구니에 있는 아이템들을 수정할 때 사용될 메서드
    @PutMapping("/api/menu/cart")
    public String updateCart(HttpServletRequest request,
                             @RequestBody List<ItemCartDto> list) {
        log.info("list = {}", list);

        HttpSession session = request.getSession();
        ArrayList<CartDto> cartDtos = new ArrayList<>();

        for (ItemCartDto dto : list) {
            cartDtos.add(new CartDto(dto.getId(), dto.getQuantity()));
        }

        session.setAttribute(SessionConst.CART, cartDtos);

        return "ok";
    }

}