package com.sadmag.macros_v2.user;

import com.sadmag.macros_v2.user.exception.UsernameOrEmailAlreadyExistsException;
import com.sadmag.macros_v2.validator.Validator;
import com.sadmag.macros_v2.user.validators.EmailValidator;
import com.sadmag.macros_v2.profile.validators.GenderValidator;
import com.sadmag.macros_v2.user.validators.PasswordValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailValidator emailValidator;

    @Mock
    private PasswordValidator passwordValidator;

    @Spy
    private List<Validator<UserDto>> validators = new ArrayList<>();

    @Autowired
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        validators.add(emailValidator);
        validators.add(passwordValidator);
    }

    @Test
    @DisplayName("save() should return the saved user")
    void shouldReturnTheSavedUser() {
        var userDto = new UserDto("teste", "teste123A!", "teste@domain.com");

        Mockito.when(userRepository.findUserByUsername(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(userRepository.findUserByEmail(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = userService.saveUser(userDto);

        Assertions.assertEquals("teste", result.getUsername());
        Assertions.assertTrue(new BCryptPasswordEncoder().matches("teste123A!", result.getPassword()));
        Assertions.assertEquals("teste@domain.com", result.getEmail());
    }

    @Test
    @DisplayName("save() should throw UsernameOrEmailAlreadyExistsException when username already exists")
    void shouldThrowUsernameOrEmailAlreadyExistsExceptionWhenUsernameAlreadyExists() {
        var userId = UUID.randomUUID();

        var userDto = new UserDto("teste", "teste123A!", "teste@domain.com");

        User user = new User();
        user = new User(userId, "teste", "teste123A!", "teste@domain.com", UserRole.USER);

        Mockito.when(userRepository.findUserByUsername("teste")).thenReturn(Optional.of(user));

        Assertions.assertThrows(UsernameOrEmailAlreadyExistsException.class, () -> userService.saveUser(userDto));
    }

    @Test
    @DisplayName("save() should throw UsernameOrEmailAlreadyExistsException when email already exists")
    void shouldThrowUsernameOrEmailAlreadyExistsExceptionWhenEmailAlreadyExists() {
        var userId = UUID.randomUUID();
        var userDto = new UserDto("teste", "teste123A!", "teste@domain.com");

        User user = new User();
        user = new User(userId, "teste", "teste123A!", "teste@domain.com", UserRole.USER);

        Mockito.when(userRepository.findUserByUsername("teste")).thenReturn(Optional.empty());
        Mockito.when(userRepository.findUserByEmail("teste@domain.com")).thenReturn(Optional.of(user));

        Assertions.assertThrows(UsernameOrEmailAlreadyExistsException.class, () -> userService.saveUser(userDto));
    }
}