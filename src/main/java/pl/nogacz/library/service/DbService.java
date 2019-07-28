package pl.nogacz.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.nogacz.library.domain.User;
import pl.nogacz.library.repository.BookHireRepository;
import pl.nogacz.library.repository.BookRepository;
import pl.nogacz.library.repository.BookTitleRepository;
import pl.nogacz.library.repository.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Dawid Nogacz on 28.07.2019
 */
@Service
public class DbService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookHireRepository bookHireRepository;

    @Autowired
    private BookTitleRepository bookTitleRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}