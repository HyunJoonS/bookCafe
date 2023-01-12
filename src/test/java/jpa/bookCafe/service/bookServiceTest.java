package jpa.bookCafe.service;

import jpa.bookCafe.Aes256;
import jpa.bookCafe.domain.Book;
import jpa.bookCafe.domain.Bookshelf;
import jpa.bookCafe.domain.enumStatus.BookStatus;
import jpa.bookCafe.dto.BookDto;
import jpa.bookCafe.repository.BookRepository;
import jpa.bookCafe.repository.BookshelfRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@SpringBootTest(classes = {BookService.class})
@ExtendWith(SpringExtension.class)
@Import({BookService.class})
class BookServiceTest {

    @MockBean BookRepository bookRepository;
    @MockBean BookshelfRepository bookshelfRepository; //책장
    @SpyBean Aes256 aes256; //암호화 모듈
    @Autowired BookService bookService;

    @DisplayName("전체 조회")
    @Test
    void findAllBookDtos() throws Exception {
        //given
        when(bookRepository.findAll()).thenReturn(bookList());
        //when
        List<BookDto> allBookDtos = bookService.findAllBookDtos();

        //then
        assertThat(allBookDtos.size()).isEqualTo(10);
    }


    @DisplayName("id로 찾기")
    @Test
    void findDtoById() throws Exception {
        //given
        long id = 1L;
        when(bookRepository.findById(id)).thenReturn(Optional.of(book(id)));

        //when
        BookDto findBook = bookService.findDtoById(id);

        //then
        assertThat(findBook.getId()).isEqualTo(id);
    }

    @DisplayName("제목, 저자로 검색")
    @Test
    void findDtoByQuery() throws Exception {
        //given
        when(bookRepository.findByQuery("나루토")).thenReturn(bookList());

        //when
        List<BookDto> bookDtos = bookService.findDtosByQuery("나루토");

        //then
        assertThat(bookDtos.size()).isEqualTo(10);
    }

    @DisplayName("업데이트")
    @Test
    void dtoSave_Update_Test() throws Exception {
        //given
        Book db_book = book(1L);
        BookDto bookDto = new BookDto(db_book);
        bookDto.setTitle("hello");
        bookDto.setPassword("1234");

        when(bookshelfRepository.findByName("가")).thenReturn(Optional.of(new Bookshelf("가")));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(db_book));
        when(bookRepository.save(any(Book.class))).thenAnswer(AdditionalAnswers.answer((argument0) -> argument0));

        //when
        Long savedId = bookService.dtoSave(bookDto);

        //then
        assertThat(savedId).isEqualTo(1L);
    }

    @DisplayName("세이브")
    @Test
    void dtoSave_Test() throws Exception {
        //given
        Book db_book = book(null);
        BookDto bookDto = new BookDto(db_book);
        bookDto.setTitle("hello");
        bookDto.setPassword("1234");

        when(bookshelfRepository.findByName("가")).thenReturn(Optional.of(new Bookshelf("가")));
        when(bookRepository.save(any(Book.class))).thenAnswer((Answer<Book>) i->{
            Book b = i.getArgument(0);
            b.setId(1L);
            return b;
        });

        //when
        Long savedId = bookService.dtoSave(bookDto);

        //then
        assertThat(savedId).isEqualTo(1L);
    }

    @DisplayName("비밀번호 실패 테스트")
    @Test
    void dtoSave_NotEqual_Password_Test() throws Exception {
        //given
        Book book = book(1L);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        //when
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            bookService.passwordAuth(1L,"1233");
        });

        //then
        assertThat(ex.getMessage()).isEqualTo("패스워드가 일치하지 않습니다.");
    }

    @DisplayName("비밀번호 검증 테스트")
    @Test
    void passwordAuth() throws Exception {
        Book book = book(1L);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.passwordAuth(1L,"1234");
    }


    @Test
    void delete() throws Exception {
        Book book = book(1L);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        bookService.delete(1L,"1234");
        verify(bookRepository,times(1)).delete(book);
    }


    private List<Book> bookList() throws Exception {
        List<Book> list = new ArrayList<>();
        for(int i=0; i<10; i++){
            Book book = book((long) i);
            list.add(book);
        }
        return list;
    }
    private Book book(Long i) throws Exception {
        Bookshelf 가 = new Bookshelf("가");
        Book book = new Book("나루토"+i, "키시모토 마사시"+i, "『나루토』 여기는 나뭇잎 마을. 닌자 학교의 문제아 나루토는 오늘도 장난질에 열중이다!! 그런 나루토의 꿈은 역대의 용사. 호카게의 이름을 물려 받아 그 누구보다 뛰어난 닌자가 되는 것. 하지만 나루토에겐 출생의 비밀이?!",
                "https://shopping-phinf.pstatic.net/main_3248605/32486053939.20221019105128.jpg",
                "20000919", "대원씨아이", 72, 가, BookStatus.O);
        book.setId(i);
        book.setCreateDateTime(LocalDateTime.now());
        book.setLastModifiedDate(LocalDateTime.now());
        book.setPassword(aes256.Encrypt("1234"));
        return book;
    }

}