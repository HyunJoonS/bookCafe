package jpa.bookCafe.repository;

import jpa.bookCafe.domain.Book;
import jpa.bookCafe.domain.Bookshelf;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BookRepository extends JpaRepository<Book,Long> {
    @Query("select b from Book b where b.title like %:query% or b.author like %:query%")
    public List<Book> findByQuery(@Param("query") String query);

    @Query("select b from Book b join b.bookshelf c where (:query is null or c.name=:query)")
    public Page<Book> findByBookshelfName(@Param("query") String query, Pageable pageable);
}
