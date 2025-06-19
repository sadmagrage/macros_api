package com.sadmag.macros_v2.user.validators.impl;

import com.sadmag.macros_v2.equation.EquationPreference;
import com.sadmag.macros_v2.user.UserDto;
import com.sadmag.macros_v2.user.exception.ValidationException;
import com.sadmag.macros_v2.user_info.UserInfoDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

class EmailValidatorTest {

    @Autowired
    @InjectMocks
    private EmailValidator emailValidator;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("validate() should do nothing when email is correct")
    void shouldDoNothingWhenEmailIsCorrect() {
        String email = "teste@domain.com";
        var birth = LocalDateTime.now();

        var userInfoDto = new UserInfoDto(82.0f, 12f, birth, 172, 'M', 1.5f, EquationPreference.TINSLEY_MUSCULAR_WEIGHT, true);
        var userDto = new UserDto("teste", "teste123A!", email, userInfoDto);

        emailValidator.validate(userDto);
    }

    @Test
    @DisplayName("validate() should throw ValidationException when email is null")
    void shouldThrowValidationExceptionWhenEmailIsNull() {
        String email = null;
        var birth = LocalDateTime.now();

        var userInfoDto = new UserInfoDto(82.0f, 12f, birth, 172, 'M', 1.5f, EquationPreference.TINSLEY_MUSCULAR_WEIGHT, true);
        var userDto = new UserDto("teste", "teste123A!", email, userInfoDto);

        Assertions.assertThrows(ValidationException.class, () -> emailValidator.validate(userDto));
    }

    @DisplayName("validate() should throw ValidationException when email is invalid")
    @ParameterizedTest
    @ValueSource(strings = {"teste@", "@.com", "teste@domain", "teste@.com", "@domain.com", "teste@domain..com", ".teste@domain.com"})
    void shouldThrowValidationExceptionWhenEmailIsInvalid(String email) {
        var birth = LocalDateTime.now();

        var userInfoDto = new UserInfoDto(82.0f, 12f, birth, 172, 'M', 1.5f, EquationPreference.TINSLEY_MUSCULAR_WEIGHT, true);
        var userDto = new UserDto("teste", "teste123A!", email, userInfoDto);

        Assertions.assertThrows(ValidationException.class, () -> emailValidator.validate(userDto));
    }
}