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

class GenderValidatorTest {

    @Autowired
    @InjectMocks
    private GenderValidator genderValidator;

    @BeforeEach
    void setup() { MockitoAnnotations.openMocks(this); }

    @Test
    @DisplayName("validate() should do nothing when gender character is \"M\"")
    void shouldDoNothingWhenGenderCharacterIsM() {
        char gender = 'M';
        var birth = LocalDateTime.now();

        var userInfoDto = new UserInfoDto(82.0f, 12f, birth, 172, gender, 1.5f, EquationPreference.TINSLEY_MUSCULAR_WEIGHT, true);
        var userDto = new UserDto("teste", "teste123A!", "teste@domain.com", userInfoDto);

        genderValidator.validate(userDto);
    }

    @Test
    @DisplayName("validate() should do nothing when gender character is \"F\"")
    void shouldDoNothingWhenGenderCharacterIsF() {
        char gender = 'F';
        var birth = LocalDateTime.now();

        var userInfoDto = new UserInfoDto(82.0f, 12f, birth, 172, gender, 1.5f, EquationPreference.TINSLEY_MUSCULAR_WEIGHT, true);
        var userDto = new UserDto("teste", "teste123A!", "teste@domain.com", userInfoDto);

        genderValidator.validate(userDto);
    }

    @ParameterizedTest
    @ValueSource(chars = {'1', 'A', 'm', 'f', '!', 'g', 'p'})
    @DisplayName("validate() should throw ValidationException when gender character is different from M and F")
    void shouldThrowValidationExceptionWhenGenderCharacterIsDifferentFromMAndF(char gender) {
        var birth = LocalDateTime.now();

        var userInfoDto = new UserInfoDto(82.0f, 12f, birth, 172, gender, 1.5f, EquationPreference.TINSLEY_MUSCULAR_WEIGHT, true);
        var userDto = new UserDto("teste", "teste123A!", "teste@domain.com", userInfoDto);

        Assertions.assertThrows(ValidationException.class, () -> genderValidator.validate(userDto));
    }
}