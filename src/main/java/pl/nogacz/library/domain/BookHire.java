package pl.nogacz.library.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author Dawid Nogacz on 28.07.2019
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "book_hires")
public class BookHire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonBackReference
    private Book book;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @NotNull
    @Column(name = "date_rental")
    private LocalDate dateRental;

    @Column(name = "date_return")
    private LocalDate dateReturn;
}
