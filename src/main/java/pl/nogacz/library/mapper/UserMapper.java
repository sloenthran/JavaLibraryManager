package pl.nogacz.library.mapper;

import org.springframework.stereotype.Component;
import pl.nogacz.library.controller.exception.BookNotFoundException;
import pl.nogacz.library.controller.exception.UserNotFoundException;
import pl.nogacz.library.domain.User;
import pl.nogacz.library.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    private BookMapper bookMapper;

    public UserMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public UserDto mapUserToUserDto(final User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getRegisterDate(),
                bookMapper.mapListBookHireToListBookHireDto(user.getBooksHire())
        );
    }

    public List<UserDto> mapListUserToListUserDto(final List<User> users) {
        return users.stream()
                .map(this::mapUserToUserDto)
                .collect(Collectors.toList());
    }

    public User mapUserDtoToUser(final UserDto userDto) throws BookNotFoundException, UserNotFoundException {
        return new User(
            userDto.getId(),
            userDto.getName(),
            userDto.getSurname(),
            userDto.getRegisterDate(),
            bookMapper.mapListBookHireDtoToListBookHire(userDto.getBookHires())
        );
    }
}
