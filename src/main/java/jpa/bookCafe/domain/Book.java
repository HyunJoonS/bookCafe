package jpa.bookCafe.domain;

import jpa.bookCafe.domain.enumStatus.BookStatus;
import jpa.bookCafe.dto.BookDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id @GeneratedValue
    private Long id;
    private String title;
    private String author;

    @Enumerated(EnumType.STRING)
    private BookStatus completionStatus;

    @Column(length = 1000)
    private String description;

    private String image;
    private String pubdate;
    private String password;

    @CreatedDate
    private LocalDateTime createDateTime;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    private String publisher;
    private int lastBooks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookshelf_id")
    private Bookshelf bookshelf;

    public Book(String title, String author, String description, String image, String pubdate, String publisher, int lastBooks, Bookshelf bookshelf, BookStatus bookStatus) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.image = image;
        this.pubdate = pubdate;
        this.publisher = publisher;
        this.lastBooks = lastBooks;
        this.bookshelf = bookshelf;
        this.completionStatus = bookStatus;
    }

    public void update(BookDto bookDto,Bookshelf bookshelf) {
        this.title = bookDto.getTitle();
        this.author = bookDto.getAuthor();
        this.completionStatus = BookStatus.valueOf(bookDto.getCompletionStatus());
        this.description = bookDto.getDescription();
        this.image = bookDto.getImage();
        this.pubdate = bookDto.getPubdate();
        this.publisher = bookDto.getPublisher();
        this.lastBooks = bookDto.getLastBooks();
        this.bookshelf = bookshelf;
    }

}
