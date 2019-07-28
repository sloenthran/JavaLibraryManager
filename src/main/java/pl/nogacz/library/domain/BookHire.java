package pl.nogacz.library.domain;

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
    @Column(name = "id", nullable = false)
    @NotNull
    private Long id;

    //TODO Add reference to Book

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @Column(name = "date_rental", nullable = false)
    @NotNull
    private LocalDate dateRental;

    @Column(name = "date_return")
    private LocalDate dateReturn;
}
