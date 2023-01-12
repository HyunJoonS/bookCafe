package jpa.bookCafe.service;

import jpa.bookCafe.Aes256;
import jpa.bookCafe.domain.Book;
import jpa.bookCafe.domain.Bookshelf;
import jpa.bookCafe.dto.BookDto;
import jpa.bookCafe.repository.BookRepository;
import jpa.bookCafe.repository.BookshelfRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class BookService {
    private final BookRepository bookRepository;
    private final BookshelfRepository bookshelfRepository; //책장
    private final Aes256 aes256; //암호화 모듈

    //조건없음 모든책 검색
    public List<BookDto> findAllBookDtos(){
        List<Book> Books = bookRepository.findAll();
        List<BookDto> bookDtos = new ArrayList<>();
        for (Book book : Books) {
            bookDtos.add(new BookDto(book));
        }
        return bookDtos;
    }

    //책장별 책에 대한 검색 페이징
    public Page<BookDto> findAllBookDtos(Pageable pageable, String bookshelf){
        Page<Book> books;
        if(bookshelf.equals("전체")){
             books = bookRepository.findAll(pageable);
        }
        else{
            books = bookRepository.findByBookshelfName(bookshelf,pageable);
        }
        Page<BookDto> book = books.map(m -> new BookDto(m));

        return book;
    }

    //책 id로 직접 찾기, 검색 결과
    public BookDto findDtoById(Long id){
        Book book = bookRepository.findById(id).get();
        BookDto bookDto = new BookDto(book.getId(), book.getTitle(), book.getAuthor(), book.getDescription(), book.getImage(),
                book.getPubdate(), book.getPublisher(), book.getLastBooks(), book.getCreateDateTime(),
                book.getLastModifiedDate(), book.getBookshelf().getName(),
                book.getCompletionStatus().toString());
        return bookDto;
    }

    //제목 또는 저자로 검색하기
    public List<BookDto> findDtosByQuery(String query) {
        //query : title or author
        List<Book> byQuery = bookRepository.findByQuery(query);
        List<BookDto> bookDtos = new ArrayList<>();
        for (Book book : byQuery) {
            bookDtos.add(new BookDto(book));
        }
        return bookDtos;
    }

    //책 저장 or 업데이트
    public Long dtoSave(BookDto bookDto) throws Exception {
        //어느 책장에 저장할것인지
        Bookshelf bookshelf = bookshelfRepository.findByName(bookDto.getBookshelf()).get();
        Book book;
        if(bookDto.getId() != null){ // 기존 책을 업데이트 하는것인가??
            passwordAuth(bookDto.getId(),bookDto.getPassword()); //책 주인인지 비번 확인
            book = bookRepository.findById(bookDto.getId()).get();

        }
        else { //새로 등록하는것 이라면
            book = new Book();
            String encrypt = aes256.Encrypt(bookDto.getPassword()); // 삭제용 비밀번호 암호화
            book.setPassword(encrypt);
        }
        book.update(bookDto,bookshelf);
        Book save = bookRepository.save(book);
        return save.getId();
    }

    //게시물 수정 삭제 권한 -> 게시물 비밀번호 인증
    public void passwordAuth(Long bookId, String password) throws Exception {
        Book book = bookRepository.findById(bookId).get();
        if(password.equals("admin1")){ //마스터키
            return;
        }
        else{
            String encrypt = aes256.Encrypt(password);
            if(book.getPassword() == null || !book.getPassword().equals(encrypt)){
                throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
            }
        }
    }

    //삭제
    public void delete(Long bookId, String password) throws Exception {
        passwordAuth(bookId,password); //비번 확인
        Book book = bookRepository.findById(bookId).get();
        bookRepository.delete(book);
    }
}
