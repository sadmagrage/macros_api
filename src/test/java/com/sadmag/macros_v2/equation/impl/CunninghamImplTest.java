package com.sadmag.macros_v2.equation.impl;

import com.sadmag.macros_v2.equation.exception.MissingValuesInEquationException;
import com.sadmag.macros_v2.user_info.UserInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.stream.Stream;

class CunninghamImplTest {

    @Autowired
    @InjectMocks
    private CunninghamImpl cunningham;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @DisplayName("calculate() should calculate the basal spent from Cunningham equation")
    @MethodSource("provideUserInfo")
    void calculate_shouldCalculateTheBasalSpentFromCunninghamEquation(UserInfo userInfo, float expectedValue) {
        var result = cunningham.calculate(userInfo);

        Assertions.assertTrue(Math.abs(expectedValue - result) < 1);
    }

    @ParameterizedTest
    @DisplayName("calculate() should throw MissingValuesException when required values are missing")
    @MethodSource("provideUserInfoWithMissingValues")
    void calculate_shouldThrowMissingValuesException_whenRequiredValuesAreMissing(UserInfo userInfo) {
        Assertions.assertThrows(MissingValuesInEquationException.class, () -> cunningham.calculate(userInfo));
    }

    static Stream<Arguments> provideUserInfo() {
        return Stream.of(
                Arguments.of(new UserInfo(null, 70.0f, 10.0f, LocalDateTime.parse("2000-01-11T00:00:00"), 175, 'M',
                        0.0f, null, false, null), 1862.4f),
                Arguments.of(new UserInfo(null, 90.0f, 10.0f, LocalDateTime.parse("1985-04-09T00:00:00"), 180, 'M',
                        0.0f, null, false, null), 2251.2f),
                Arguments.of(new UserInfo(null, 60.0f, 10.0f, LocalDateTime.parse("1995-06-30T00:00:00"), 165, 'F',
                        0.0f, null, false, null), 1668.0f),
                Arguments.of(new UserInfo(null, 70.0f, 10.0f, LocalDateTime.parse("1975-12-31T00:00:00"), 160, 'F',
                        0.0f, null, false, null), 1862.4f));
    }

    static Stream<Arguments> provideUserInfoWithMissingValues() {
        return Stream.of(
                Arguments.of(new UserInfo(null, 0.0f, 10.0f, LocalDateTime.parse("2000-01-11T00:00:00"), 175, 'M',
                        0.0f, null, false, null), 1915.7f),
                Arguments.of(new UserInfo(null, 90.0f, 0.0f, LocalDateTime.parse("1985-04-09T00:00:00"), 180, 'M',
                        0.0f, null, false, null), 2381.9f));
    }
}