package pl.nogacz.library.service;

import org.springframework.stereotype.Service;
import pl.nogacz.library.controller.exception.UserNotFoundException;
import pl.nogacz.library.domain.User;
import pl.nogacz.library.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User getUser(Long id) throws UserNotFoundException {
        return repository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public User saveUser(User user) {
        return repository.save(user);
    }
}
