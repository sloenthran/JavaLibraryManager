package pl.nogacz.library.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.nogacz.library.domain.Book;
import javax.transaction.Transactional;

/**
 * @author Dawid Nogacz on 28.07.2019
 */
@Transactional
@Repository
public interface BookRepository extends CrudRepository<Book, Long> {}