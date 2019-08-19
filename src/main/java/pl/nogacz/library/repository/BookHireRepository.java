package pl.nogacz.library.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.nogacz.library.domain.BookHire;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author Dawid Nogacz on 28.07.2019
 */
@Transactional
@Repository
public interface BookHireRepository extends CrudRepository<BookHire, Long> {
    Optional<BookHire> findByUser_IdAndBook_Id(Long userId, Long BookId);
}