package com.sadmag.macros_v2.user.validators;

import com.sadmag.macros_v2.user.UserDto;
import com.sadmag.macros_v2.user.exception.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

class PasswordValidatorTest {

    @Autowired
    @InjectMocks
    private PasswordValidator passwordValidator;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("validate() should do nothing when password is correct")
    void shouldDoNothingWhenPasswordIsCorrect() {
        String password = "teste123!A";

        var userDto = new UserDto("teste", password, "teste@domain.com");

        passwordValidator.validate(userDto);
    }

    @Test
    @DisplayName("validate() should throw ValidationException when password is null")
    void shouldThrowValidationExceptionWhenPasswordIsNull() {
        String password = null;

        var userDto = new UserDto("teste", password, "teste@domain.com");

        Assertions.assertThrows(ValidationException.class, () -> passwordValidator.validate(userDto));
    }

    @Test
    @DisplayName("validate() should throw ValidationException when password length is under 8 characters")
    void shouldThrowValidationExceptionWhenPasswordLengthIsUnder8Characters() {
        String password = "test!2A";

        var userDto = new UserDto("teste", password, "teste@domain.com");

        Assertions.assertThrows(ValidationException.class, () -> passwordValidator.validate(userDto));
    }

    @Test
    @DisplayName("validate() should throw ValidationException when password does not contain numbers")
    void shouldThrowValidationExceptionWhenPasswordDoesNotContainNumbers() {
        String password = "teste!A#B";

        var userDto = new UserDto("teste", password, "teste@domain.com");

        Assertions.assertThrows(ValidationException.class, () -> passwordValidator.validate(userDto));
    }

    @Test
    @DisplayName("validate() should throw ValidationException when password does not contain lower case")
    void shouldThrowValidationExceptionWhenPasswordDoesNotContainLowerCase() {
        String password = "TESTE!23@A";

        var userDto = new UserDto("teste", password, "teste@domain.com");

        Assertions.assertThrows(ValidationException.class, () -> passwordValidator.validate(userDto));
    }

    @Test
    @DisplayName("validate() should throw ValidationException when password does not contain upper case")
    void shouldThrowValidationExceptionWhenPasswordDoesNotContainUpperCase() {
        String password = "teste!23@a";

        var userDto = new UserDto("teste", password, "teste@domain.com");

        Assertions.assertThrows(ValidationException.class, () -> passwordValidator.validate(userDto));
    }

    @Test
    @DisplayName("validate() should throw ValidationException when password does not contain special characters")
    void shouldThrowValidationExceptionWhenPasswordDoesNotContainSpecialCharacters() {
        String password = "testE12A3";

        var userDto = new UserDto("teste", password, "teste@domain.com");

        Assertions.assertThrows(ValidationException.class, () -> passwordValidator.validate(userDto));
    }
}