package pl.nogacz.library.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dawid Nogacz on 28.07.2019
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @NotNull
    private Long id;

    @Column(name = "name", nullable = false)
    @NotNull
    private String name;

    @Column(name = "surname", nullable = false)
    @NotNull
    private String surname;

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    @OneToMany(
            targetEntity = BookHire.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<BookHire> booksHire = new ArrayList<>();
}
