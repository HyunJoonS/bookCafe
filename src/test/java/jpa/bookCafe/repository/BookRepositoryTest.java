package jpa.bookCafe.repository;

import jpa.bookCafe.domain.Book;
import jpa.bookCafe.domain.Bookshelf;
import jpa.bookCafe.domain.enumStatus.BookStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class BookRepositoryTest {

    @Autowired BookRepository bookRepository;
    @Autowired BookshelfRepository bookshelfRepository;

    @BeforeEach
    void setup(){
        Bookshelf 가 = bookshelfRepository.save(new Bookshelf("가"));
        Book book = new Book();
        book.setTitle("나루토");
        book.setBookshelf(가);
        bookRepository.save(book);
    }

    @DisplayName("제목,저자로 검색")
    @Test
    void findByQuery() {
        List<Book> list = bookRepository.findByQuery("나루토");
        for(int i=0; i<list.size(); i++){
            Book book = list.get(i);
            Assertions.assertThat(book.getTitle()).isEqualTo("나루토");
            System.out.println(book.getTitle());
        }
    }

    @DisplayName("책장으로 페이징 검색")
    @Test
    void findByBookshelfName() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Book> page = bookRepository.findByBookshelfName("가", pageable);

        List<Book> content = page.getContent();

        Assertions.assertThat(content.get(0).getBookshelf().getName()).isEqualTo("가");
        Assertions.assertThat(content.get(0).getTitle()).isEqualTo("나루토");
    }
}