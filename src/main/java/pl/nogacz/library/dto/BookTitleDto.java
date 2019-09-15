package pl.nogacz.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookTitleDto {
    private Long id;
    private String title;
    private String author;
    private LocalDate publicationDate;
}
