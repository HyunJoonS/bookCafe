package jpa.bookCafe.controller;

import jpa.bookCafe.dto.BookDto;
import jpa.bookCafe.repository.BookRepository;
import jpa.bookCafe.repository.ItemRepository;
import jpa.bookCafe.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final ItemRepository itemRepository;

    @GetMapping("/err/{id}")
    public String getMember(@PathVariable("id") String id) {
        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }
        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력 값");
        }
        return id;
    }


    @GetMapping("/bookSearch")
    public String BookSearch(@RequestParam("query") String query,
                             @RequestParam("display") String display,
                             @RequestParam("start") String start){
        log.info("요청 {},{},{}",query,display,start);
        String API_ID = "tJonrAzZb7_ojoWfWQBa";
        String API_KEY = "_dgE8mr70g";
        WebClient webClient = WebClient
                .builder()
                .baseUrl("https://openapi.naver.com/v1/search/book.json")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add("X-Naver-Client-Id",API_ID);
                    httpHeaders.add("X-Naver-Client-Secret",API_KEY);
                })
                .build();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("query", query);
        params.add("display", display);
        params.add("start", start);

        String response = webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.queryParams(params).build()
                ).retrieve().bodyToMono(String.class).block();
        return response;
    }


    @GetMapping("/bookList")
    public List<BookDto> BookList(){
        List<BookDto> allBookDtos = bookService.findAllBookDtos();
        return allBookDtos;
    }
    @GetMapping("/book/list")
    public Page<BookDto> BookList(Pageable pageable,@RequestParam(value = "bookshelf",required = false)String bookshelf){
        Page<BookDto> page = bookService.findAllBookDtos(pageable, bookshelf);
        return page;
    }
    @GetMapping("/api/book/detail/{id}")
    public BookDto BookDto(@PathVariable("id")Long id){
        log.info("id={}",id);
        BookDto dto = bookService.findDtoById(id);
        return dto;
    }

    @GetMapping("/api/book/search")
    public List<BookDto> dtoByQuery(@RequestParam("searchQuery")String query){
        log.info("query={}",query);
        List<BookDto> dtoByQuery = bookService.findDtoByQuery(query);
        return dtoByQuery;
    }

    @PostMapping("/api/book")
    public Long BookAdd(@RequestBody BookDto bookDto) throws Exception {
        Long savedId = bookService.dtoSave(bookDto);
        return savedId;
    }
    @DeleteMapping("/api/book")
    public String BookDelete(@RequestParam("id") Long id, @RequestParam("password") String pw) throws Exception {
        log.info("id={}, pw={}",id,pw);
        bookService.delete(id, pw);
        return "ok";
    }
    @PutMapping("/api/book")
    public Long bookUpdate(@RequestBody BookDto bookDto) throws Exception {
        log.info("bookDto={}",bookDto);
        Long bookId = bookService.dtoSave(bookDto);
        return bookId;
    }
}
