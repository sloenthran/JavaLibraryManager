package pl.nogacz.library.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.nogacz.library.domain.BookTitle;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookTitleRepository extends CrudRepository<BookTitle, Long> {
    List<BookTitle> findAll();
    Optional<BookTitle> findByTitleLike(String title);
}