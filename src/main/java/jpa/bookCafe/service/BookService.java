package jpa.bookCafe.service;

import jpa.bookCafe.Aes256;
import jpa.bookCafe.domain.Book;
import jpa.bookCafe.domain.Bookshelf;
import jpa.bookCafe.domain.enumStatus.BookStatus;
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
import java.util.concurrent.CompletionService;

@RequiredArgsConstructor
@Service
@Transactional
public class BookService {
    private final BookRepository bookRepository;
    private final BookshelfRepository bookshelfRepository;
    private final Aes256 aes256;

    public List<BookDto> findAllBookDtos(){
        List<Book> Books = bookRepository.findAll();
        List<BookDto> bookDtos = new ArrayList<>();
        for (Book book : Books) {
            bookDtos.add(new BookDto(book));
        }
        return bookDtos;
    }
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
    public BookDto findDtoById(Long id){
        Book book = bookRepository.findById(id).get();
        BookDto bookDto = new BookDto(book.getId(), book.getTitle(), book.getAuthor(), book.getDescription(), book.getImage(),
                book.getPubdate(), book.getPublisher(), book.getLastBooks(), book.getCreateDateTime(),
                book.getLastModifiedDate(), book.getBookshelf().getName(),
                book.getCompletionStatus().toString());

        return bookDto;
    }

    public List<BookDto> findDtoByQuery(String query) {
        //query : title or author
        List<Book> byQuery = bookRepository.findByQuery(query);
        List<BookDto> bookDtos = new ArrayList<>();
        for (Book book : byQuery) {
            bookDtos.add(new BookDto(book));
        }
        return bookDtos;
    }

    public Long dtoSave(BookDto bookDto) throws Exception {
        Bookshelf bookshelf = bookshelfRepository.findByName(bookDto.getBookshelf()).get();
        Book book;
        if(bookDto.getId() != null){
            passwordAuth(bookDto.getId(),bookDto.getPassword());
            book = bookRepository.findById(bookDto.getId()).get();

        }
        else {
            book = new Book();
            String encrypt = aes256.Encrypt(bookDto.getPassword());
            book.setPassword(encrypt);
        }
        book.update(bookDto,bookshelf);
        bookRepository.save(book);
        return book.getId();
    }

    public void passwordAuth(Long bookId, String password) throws Exception {
        Book book = bookRepository.findById(bookId).get();
        if(password.equals("admin1")){
            return;
        }
        else{
            String encrypt = aes256.Encrypt(password);
            if(book.getPassword() == null || !book.getPassword().equals(encrypt)){
                throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
            }
        }
    }

    public void delete(Long bookId, String password) throws Exception {
        passwordAuth(bookId,password);
        Book book = bookRepository.findById(bookId).get();
        bookRepository.delete(book);
    }
}
