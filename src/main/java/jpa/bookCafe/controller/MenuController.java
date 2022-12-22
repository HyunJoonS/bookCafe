package jpa.bookCafe.controller;

import jpa.bookCafe.dto.CartDto;
import jpa.bookCafe.dto.ItemCartDto;
import jpa.bookCafe.dto.ItemDto;
import jpa.bookCafe.file.FileStore;
import jpa.bookCafe.kakaoPay.KakaoPayService;
import jpa.bookCafe.repository.ItemRepository;
import jpa.bookCafe.service.ItemService;
import jpa.bookCafe.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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

    @PostMapping("/api/menu")
    public String MenuAdd(@RequestPart MultipartFile file,
                          @RequestPart("data") ItemDto itemDto) throws Exception {
        log.info("file {}", file);
        log.info("item {}", itemDto);
        if(!file.isEmpty()){
            String storeFile = fileStore.storeFile(file);
            itemDto.setPhotoPath(storeFile);
            itemService.add_item(itemDto);
        }
        return "ok";
    }

    @DeleteMapping("/api/menu")
    public String MenuDelete(@RequestParam("id") Long id, @RequestParam("password") String pw) throws Exception {
        log.info("id={}, pw={}",id,pw);
        itemService.delete(id, pw);
        return "ok";
    }

    @GetMapping("/api/menu")
    public List<ItemDto> MenuList(){
        List<ItemDto> itemDto = itemRepository.findItemDto();
        log.info("{}",itemDto);
        return itemDto;
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        // UrlResource로 파일을 읽어서 @ResponseBody로 이미지 바이너리를 반환한다.
        // fullPath로 전체 경로 /Users/... 를 가져온다.
        return new UrlResource("file:" + fileDir + filename);
    }

    @PostMapping("/api/menu/cart")
    public String menuInCart(HttpServletRequest request,
                           CartDto cartDto){

        log.info("넘어온값 = {}",cartDto);
        HttpSession session = request.getSession();


        List<CartDto> attribute = (List<CartDto>) session.getAttribute(SessionConst.CART);
        log.info("att={}, sessionID={}",attribute,session.getId());

        if(attribute != null){
            long count = attribute.stream().filter(dto -> dto.getItemId() == cartDto.getItemId()).count();
            log.info("count = {}");
            if(count>0){
                return "이미 장바구니에 추가된 상품입니다.";
            }
            attribute.add(cartDto);
        }
        else{
            List<CartDto> list = new ArrayList<>();
            list.add(cartDto);
            session.setAttribute(SessionConst.CART,list);
            log.info("list = {}",list);
        }
        return "장바구니에 추가되었습니다.";

    }

    @GetMapping("/api/menu/cart")
    public List<ItemCartDto> cartList(HttpServletRequest request){
        HttpSession session = request.getSession(false);

        if(session != null){
            List<CartDto> attribute = (List<CartDto>) session.getAttribute(SessionConst.CART);
            log.info("attr={}",attribute);
            if(attribute != null){
                List<Long> ids = new ArrayList<>();
                for (CartDto dto : attribute) {
                    log.info("dto={}",dto);
                    ids.add(dto.getItemId());
                }
                Long[] longs = ids.stream().toArray(Long[]::new);
                List<ItemDto> itemDtoByIds = itemRepository.findItemDtoByIds(longs);
                log.info("itemdtos~ = {}",itemDtoByIds);

                List<CartDto> sortedAttribute = attribute.stream().sorted(Comparator.comparing((CartDto c) -> c.getItemId())).collect(Collectors.toList());
                log.info("sorted attribute={}",sortedAttribute);
                List<ItemDto> sortedItemDtos = itemDtoByIds.stream().sorted(Comparator.comparing((ItemDto i) -> i.getId())).collect(Collectors.toList());
                log.info("sorted sortedItemDtos={}",sortedItemDtos);


                List<ItemCartDto> result = new ArrayList<>();
                for (int i = 0; i < sortedItemDtos.stream().count(); i++) {
                    ItemDto itemDto = sortedItemDtos.get(i);
                    CartDto cartDto = sortedAttribute.get(i);
                    result.add(new ItemCartDto(itemDto,cartDto.getQuantity()));
                }

                return result;
            }
        }
        return null;
    }

    @DeleteMapping("/api/menu/cart")
    public String cartClear(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session != null){
            session.removeAttribute(SessionConst.CART);
        }
        return "장바구니를 비웠습니다.";
    }

    @PutMapping("/api/menu/cart")
    public String updateCart(HttpServletRequest request,
                             @RequestBody List<ItemCartDto> list){
        log.info("list = {}", list);

        HttpSession session = request.getSession();
        ArrayList<CartDto> cartDtos = new ArrayList<>();

        for (ItemCartDto dto : list) {
            cartDtos.add(new CartDto(dto.getId(),dto.getQuantity()));
        }

        session.setAttribute(SessionConst.CART,cartDtos);

        return "ok";
    }

//    @PostMapping("/menu/cart/order")
//    public ReadyResponse Order(HttpServletRequest request,
//                               @RequestBody List<ItemCartDto> list){
//        List<CartDto> cartDtos = new ArrayList<>();
//        for (ItemCartDto itemCartDto : list) {
//            cartDtos.add(new CartDto(itemCartDto.getId(), itemCartDto.getQuantity()));
//        }
//
//        log.info("cartDto={}", cartDtos);
//        Long orderId = orderService.주문하기(cartDtos);
//        Order order = orderService.주문조회(orderId);
//        ReadyResponse readyResponse = kakaoPayService.payReady(order);
//
//        HttpSession session = request.getSession();
//        session.setAttribute("orderId",orderId);
//        session.setAttribute("tid",readyResponse.getTid());
//
//        return readyResponse;
//    }


}

//    2 아 레르케 드디어
//7 전화를받았구나 너한테정말 많
//    * 은 메세지를 남겼었어 잠깐 아직 화
//5 난건 알지만, 끊지마. 이건꼭말
//    4 해야해 내가 떠나기 전에 하나만들어둔게 있
//9 거든. 너랑친구가 될녀석 말이
//    3 지.진작말했어야했는데 어쨋든 내말듣고
//~ 있어? 사라진 네까마귀알지? 네가 이름을
//    0 kol이라고지은거? 널위해새로하나만들었어
//8 위층에가봐 훨씬더좋을거야 죽지않는녀석이거든

