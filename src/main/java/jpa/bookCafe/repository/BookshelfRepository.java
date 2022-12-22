package jpa.bookCafe.repository;

import jpa.bookCafe.domain.Bookshelf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookshelfRepository extends JpaRepository<Bookshelf,Long> {
    public Optional<Bookshelf> findByName(String name);
}
