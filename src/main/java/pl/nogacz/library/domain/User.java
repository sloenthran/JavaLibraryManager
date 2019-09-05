package pl.nogacz.library.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "surname")
    private String surname;

    @NotNull
    @Column(name = "date")
    private LocalDate date;

    @OneToMany(
            targetEntity = BookHire.class,
            mappedBy = "user",
            fetch = FetchType.EAGER
    )
    @JsonManagedReference
    private List<BookHire> booksHire = new ArrayList<>();
}
