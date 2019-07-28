package pl.nogacz.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.nogacz.library.controller.exception.UserNotFoundException;
import pl.nogacz.library.domain.User;
import pl.nogacz.library.repository.UserRepository;

import java.util.List;

/**
 * @author Dawid Nogacz on 28.07.2019
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/v1/user", produces = "application/json")
public class UserController {
    private UserRepository repository;

    public UserController(@Autowired UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "getUser")
    public User getUser(@RequestParam("id") Long id) throws UserNotFoundException {
        return repository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @GetMapping(value = "getUsers")
    public List<User> getUsers() {
        return repository.findAll();
    }

    @PostMapping(value = "addUser", consumes = "application/json")
    public void addUser(@RequestBody User user) {
        repository.save(user);
    }

    @PutMapping(value = "updateUser", consumes = "application/json")
    public User updateUser(@RequestBody User user) {
        return repository.save(user);
    }
}