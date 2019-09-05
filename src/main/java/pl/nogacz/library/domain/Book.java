package pl.nogacz.library.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "title_id")
    @JsonBackReference
    private BookTitle bookTitle;

    @NotNull
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;

    @OneToMany(
            targetEntity = BookHire.class,
            mappedBy = "book",
            fetch = FetchType.EAGER
    )
    @JsonManagedReference
    private List<BookHire> bookHires = new ArrayList<>();
}