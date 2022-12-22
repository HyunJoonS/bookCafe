package jpa.bookCafe.dto;
import jpa.bookCafe.domain.Book;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class BookDto {
    private Long Id;
    private String title; //제목
    private String author; //저자
    private String description; //줄거리
    private String image; //이미지
    private String pubdate; //출판일
    private String publisher; //출판사
    private int lastBooks; //마지막권수
    private String CreateDateTime; //등록일
    private String LastModifiedDate; //마지막수정일
    private String bookshelf;
    private String completionStatus;
    private String password;

    public BookDto(Long Id,String title, String author, String description, String image, String pubdate, String publisher, int lastBooks, LocalDateTime createDateTime, LocalDateTime lastModifiedDate, String bookshelf,String completionStatus) {
        this.Id = Id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.image = image;
        this.pubdate = pubdate;
        this.publisher = publisher;
        this.lastBooks = lastBooks;
        CreateDateTime = createDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LastModifiedDate = lastModifiedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.bookshelf = bookshelf;
        this.completionStatus = completionStatus;
    }

    public BookDto(Book book) {
        Id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.description = book.getDescription();
        this.image = book.getImage();
        this.pubdate = book.getPubdate();
        this.publisher = book.getPublisher();
        this.lastBooks = book.getLastBooks();
        CreateDateTime = book.getCreateDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));;
        LastModifiedDate = book.getLastModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.bookshelf = book.getBookshelf().getName();
        this.completionStatus = book.getCompletionStatus().toString();
    }
}
