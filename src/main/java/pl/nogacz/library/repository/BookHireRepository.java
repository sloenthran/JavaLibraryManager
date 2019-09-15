package pl.nogacz.library.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.nogacz.library.domain.BookHire;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookHireRepository extends CrudRepository<BookHire, Long> {
    List<BookHire> findAll();
    Optional<BookHire> findByUser_IdAndBook_Id(Long userId, Long BookId);
}