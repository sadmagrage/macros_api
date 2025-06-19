package com.sadmag.macros_v2.user;

import com.sadmag.macros_v2.equation.EquationPreference;
import com.sadmag.macros_v2.user.exception.UsernameOrEmailAlreadyExistsException;
import com.sadmag.macros_v2.user.validators.Validator;
import com.sadmag.macros_v2.user.validators.impl.EmailValidator;
import com.sadmag.macros_v2.user.validators.impl.GenderValidator;
import com.sadmag.macros_v2.user.validators.impl.PasswordValidator;
import com.sadmag.macros_v2.user_info.UserInfo;
import com.sadmag.macros_v2.user_info.UserInfoDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
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

    @Mock
    private GenderValidator genderValidator;

    @Spy
    private List<Validator<UserDto>> validators = new ArrayList<>();

    @Autowired
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        validators.add(emailValidator);
        validators.add(genderValidator);
        validators.add(passwordValidator);
    }

    @Test
    @DisplayName("save() should return the saved user")
    void shouldReturnTheSavedUser() {
        var birth = LocalDateTime.now();

        var userInfoDto = new UserInfoDto(82.0f, 12f, birth, 172, 'M', 1.5f, EquationPreference.TINSLEY_MUSCULAR_WEIGHT, true);
        var userDto = new UserDto("teste", "teste123A!", "teste@domain.com", userInfoDto);

        Mockito.when(userRepository.findUserByUsername(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(userRepository.findUserByEmail(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = userService.saveUser(userDto);

        Assertions.assertEquals("teste", result.getUsername());
        Assertions.assertTrue(new BCryptPasswordEncoder().matches("teste123A!", result.getPassword()));
        Assertions.assertEquals("teste@domain.com", result.getEmail());

        Assertions.assertEquals(82f, result.getUserInfo().getWeight());
        Assertions.assertEquals(12f, result.getUserInfo().getBodyfat());
        Assertions.assertEquals(birth, result.getUserInfo().getBirth());
        Assertions.assertEquals(172, result.getUserInfo().getHeight());
        Assertions.assertEquals('M', result.getUserInfo().getGender());
        Assertions.assertEquals(1.5, result.getUserInfo().getActivityFactor());
        Assertions.assertEquals(EquationPreference.TINSLEY_MUSCULAR_WEIGHT, result.getUserInfo().getEquationPreference());
        Assertions.assertTrue(result.getUserInfo().isMacroInfoPublic());
    }

    @Test
    @DisplayName("save() should throw UsernameOrEmailAlreadyExistsException when username already exists")
    void shouldThrowUsernameOrEmailAlreadyExistsExceptionWhenUsernameAlreadyExists() {
        var userId = UUID.randomUUID();
        var userInfoId = UUID.randomUUID();
        var birth = LocalDateTime.now();

        var userInfoDto = new UserInfoDto(82.0f, 12f, birth, 172, 'M', 1.5f, EquationPreference.TINSLEY_MUSCULAR_WEIGHT, true);
        var userDto = new UserDto("teste", "teste123A!", "teste@domain.com", userInfoDto);

        User user = new User();
        var userInfo = new UserInfo(userInfoId, 82.0f, 12f, birth, 172, 'M', 1.5f, EquationPreference.TINSLEY_MUSCULAR_WEIGHT, true, user);
        user = new User(userId, "teste", "teste123A!", "teste@domain.com", UserRole.USER, userInfo);

        Mockito.when(userRepository.findUserByUsername("teste")).thenReturn(Optional.of(user));

        Assertions.assertThrows(UsernameOrEmailAlreadyExistsException.class, () -> userService.saveUser(userDto));
    }

    @Test
    @DisplayName("save() should throw UsernameOrEmailAlreadyExistsException when email already exists")
    void shouldThrowUsernameOrEmailAlreadyExistsExceptionWhenEmailAlreadyExists() {
        var userId = UUID.randomUUID();
        var userInfoId = UUID.randomUUID();
        var birth = LocalDateTime.now();

        var userInfoDto = new UserInfoDto(82.0f, 12f, birth, 172, 'M', 1.5f, EquationPreference.TINSLEY_MUSCULAR_WEIGHT, true);
        var userDto = new UserDto("teste", "teste123A!", "teste@domain.com", userInfoDto);

        User user = new User();
        var userInfo = new UserInfo(userInfoId, 82.0f, 12f, birth, 172, 'M', 1.5f, EquationPreference.TINSLEY_MUSCULAR_WEIGHT, true, user);
        user = new User(userId, "teste", "teste123A!", "teste@domain.com", UserRole.USER, userInfo);

        Mockito.when(userRepository.findUserByUsername("teste")).thenReturn(Optional.empty());
        Mockito.when(userRepository.findUserByEmail("teste@domain.com")).thenReturn(Optional.of(user));

        Assertions.assertThrows(UsernameOrEmailAlreadyExistsException.class, () -> userService.saveUser(userDto));
    }
}