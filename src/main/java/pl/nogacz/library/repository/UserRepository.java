package pl.nogacz.library.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.nogacz.library.domain.User;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Dawid Nogacz on 28.07.2019
 */
@Transactional
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Override
    List<User> findAll();
}