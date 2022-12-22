package jpa.bookCafe.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Bookshelf {
    @GeneratedValue @Id
    private Long Id;
    private  String name;
    @OneToMany(mappedBy = "bookshelf",cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();


    public Bookshelf(String name) {
        this.name = name;
    }
}
