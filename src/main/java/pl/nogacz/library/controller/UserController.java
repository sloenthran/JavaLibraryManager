package pl.nogacz.library.controller;

import org.springframework.web.bind.annotation.*;
import pl.nogacz.library.controller.exception.UserNotFoundException;
import pl.nogacz.library.domain.User;
import pl.nogacz.library.service.UserService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/v1/user", produces = "application/json")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "getUser")
    public User getUser(@RequestParam("id") Long id) throws UserNotFoundException {
        return userService.getUser(id);
    }

    @GetMapping(value = "getUsers")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping(value = "addUser", consumes = "application/json")
    public void addUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    @PutMapping(value = "updateUser", consumes = "application/json")
    public User updateUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
}