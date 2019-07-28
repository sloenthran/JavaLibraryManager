package pl.nogacz.library.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.nogacz.library.domain.BookTitle;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author Dawid Nogacz on 28.07.2019
 */
@Transactional
@Repository
public interface BookTitleRepository extends CrudRepository<BookTitle, Long> {
    Optional<BookTitle> findByTitleLike(String title);
}