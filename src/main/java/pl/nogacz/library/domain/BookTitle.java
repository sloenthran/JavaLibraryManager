package pl.nogacz.library.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "book_titles")
public class BookTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "author")
    private String author;

    @NotNull
    @Column(name = "date")
    private LocalDate date;

    @OneToMany(
            targetEntity = Book.class,
            mappedBy = "bookTitle",
            fetch = FetchType.EAGER
    )
    @JsonManagedReference
    private List<Book> books = new ArrayList<>();
}
