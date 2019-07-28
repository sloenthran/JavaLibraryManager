package pl.nogacz.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.nogacz.library.controller.exception.UserNotFoundException;
import pl.nogacz.library.domain.User;
import pl.nogacz.library.service.DbService;

import java.util.List;

/**
 * @author Dawid Nogacz on 28.07.2019
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/user", produces = "application/json")
public class UserController {
    @Autowired
    private DbService dbService;

    @GetMapping(value = "getUser")
    public User getUser(@RequestParam("id") Long id) throws UserNotFoundException {
        return dbService.getUser(id).orElseThrow(UserNotFoundException::new);
    }

    @GetMapping(value = "getUsers")
    public List<User> getUsers() {
        return dbService.getUsers();
    }

    @PostMapping(value = "addUser")
    public void addUser(@RequestBody User user) {
        dbService.addUser(user);
    }
}