package pl.nogacz.library.controller;

import org.springframework.web.bind.annotation.*;
import pl.nogacz.library.controller.exception.BookNotFoundException;
import pl.nogacz.library.controller.exception.UserNotFoundException;
import pl.nogacz.library.dto.UserDto;
import pl.nogacz.library.mapper.UserMapper;
import pl.nogacz.library.service.UserService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/v1/user", produces = "application/json")
public class UserController {
    private UserService userService;
    private UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping(value = "getUser")
    public UserDto getUser(@RequestParam("id") Long id) throws UserNotFoundException {
        return userMapper.mapUserToUserDto(userService.getUser(id));
    }

    @GetMapping(value = "getUsers")
    public List<UserDto> getUsers() {
        return userMapper.mapListUserToListUserDto(userService.getUsers());
    }

    @PostMapping(value = "addUser", consumes = "application/json")
    public void addUser(@RequestBody UserDto user) throws BookNotFoundException, UserNotFoundException {
        userService.saveUser(userMapper.mapUserDtoToUser(user));
    }

    @PutMapping(value = "updateUser", consumes = "application/json")
    public UserDto updateUser(@RequestBody UserDto user) throws BookNotFoundException, UserNotFoundException {
        return userMapper.mapUserToUserDto(userService.saveUser(userMapper.mapUserDtoToUser(user)));
    }
}