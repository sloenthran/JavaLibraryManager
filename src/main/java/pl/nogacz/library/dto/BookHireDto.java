package pl.nogacz.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookHireDto {
    private Long id;
    private Long bookId;
    private Long userId;
    private LocalDate dateRental;
    private LocalDate dateReturn;
}