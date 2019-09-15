package pl.nogacz.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.nogacz.library.domain.BookStatus;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDto {
    private Long id;
    private Long titleId;
    private BookStatus bookStatus;
    private List<BookHireDto> bookHires;
}
