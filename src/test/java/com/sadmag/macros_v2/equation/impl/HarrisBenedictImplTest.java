package com.sadmag.macros_v2.equation.impl;

import com.sadmag.macros_v2.date.DateUtils;
import com.sadmag.macros_v2.equation.exception.MissingValuesInEquationException;
import com.sadmag.macros_v2.user_info.UserInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.stream.Stream;

class HarrisBenedictImplTest {

    @Mock
    private DateUtils dateUtils;

    @Autowired
    @InjectMocks
    private HarrisBenedictImpl harrisBenedictEquation;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @DisplayName("calculate() should calculate the basal spent from Harris Benedict equation when birth has occurred")
    @MethodSource("provideUserInfoBirthOccurred")
    void calculate_shouldCalculateUsingHarrisBenedictFormulaWhenBirthHasOccurred(UserInfo userInfo, float expectedValue) {
        Mockito.when(dateUtils.getCurrentTime()).thenReturn(LocalDateTime.parse("2025-11-07T16:59:00"));
        Mockito.when(dateUtils.hasDateOccurredThisYear(Mockito.any(), Mockito.any())).thenReturn(true);

        var result = harrisBenedictEquation.calculate(userInfo);

        Assertions.assertTrue(Math.abs(expectedValue - result) < 1);
    }

    @ParameterizedTest
    @DisplayName("calculate() should calculate using Harris Benedict equation when birth has not occurred yet")
    @MethodSource("provideUserInfoBirthHasNotOccurredYet")
    void calculate_shouldCalculateUsingHarrisBenedictFormulaWhenBirthNotOccurredYet(UserInfo userInfo, float expectedValue) {
        Mockito.when(dateUtils.getCurrentTime()).thenReturn(LocalDateTime.parse("2025-01-10T16:59:00"));
        Mockito.when(dateUtils.hasDateOccurredThisYear(Mockito.any(), Mockito.any())).thenReturn(false);

        var result = harrisBenedictEquation.calculate(userInfo);

        Assertions.assertTrue(Math.abs(expectedValue - result) < 1);
    }

    @ParameterizedTest
    @DisplayName("calculate() should throw MissingValuesException when required values are missing")
    @MethodSource("provideUserInfoBirthWithMissingValues")
    void calculate_shouldThrowMissingValuesException_whenRequiredValuesAreMissing(UserInfo userInfo) {
        Assertions.assertThrows(MissingValuesInEquationException.class, () -> harrisBenedictEquation.calculate(userInfo));
    }

    static Stream<Arguments> provideUserInfoBirthOccurred() {
        return Stream.of(
                Arguments.of(new UserInfo(null, 70.0f, 10.0f, LocalDateTime.parse("2000-01-11T00:00:00"), 175, 'M',
                        0.0f, null, false, null), 1735.78f),
                Arguments.of(new UserInfo(null, 90.0f, 10.0f, LocalDateTime.parse("1985-04-09T00:00:00"), 180, 'M',
                        0.0f, null, false, null), 1934.51f),
                Arguments.of(new UserInfo(null, 60.0f, 10.0f, LocalDateTime.parse("1995-06-30T00:00:00"), 165, 'F',
                        0.0f, null, false, null), 1393.81f),
                Arguments.of(new UserInfo(null, 70.0f, 10.0f, LocalDateTime.parse("1975-12-31T00:00:00"), 160, 'F',
                        0.0f, null, false, null), 1386.68f));
    }

    static Stream<Arguments> provideUserInfoBirthHasNotOccurredYet() {
        return Stream.of(
                Arguments.of(new UserInfo(null, 70.0f, 10.0f, LocalDateTime.parse("2000-01-11T00:00:00"), 175, 'M',
                        0.0f, null, false, null), 1742.54f),
                Arguments.of(new UserInfo(null, 90.0f, 10.0f, LocalDateTime.parse("1985-04-09T00:00:00"), 180, 'M',
                        0.0f, null, false, null), 1941.26f),
                Arguments.of(new UserInfo(null, 60.0f, 10.0f, LocalDateTime.parse("1995-06-30T00:00:00"), 165, 'F',
                        0.0f, null, false, null), 1398.49f),
                Arguments.of(new UserInfo(null, 70.0f, 10.0f, LocalDateTime.parse("1975-12-31T00:00:00"), 160, 'F',
                        0.0f, null, false, null), 1391.36f));
    }

    static Stream<Arguments> provideUserInfoBirthWithMissingValues() {
        return Stream.of(
                Arguments.of(new UserInfo(null, 0.0f, 10.0f, LocalDateTime.parse("2000-01-11T00:00:00"), 175, 'M',
                        0.0f, null, false, null)),
                Arguments.of(new UserInfo(null, 70.0f, 10.0f, LocalDateTime.MIN, 175, 'M',
                        0.0f, null, false, null)),
                Arguments.of(new UserInfo(null, 70.0f, 10.0f, LocalDateTime.parse("2000-01-11T00:00:00"), 0, 'M',
                        0.0f, null, false, null)),
                Arguments.of(new UserInfo(null, 70.0f, 10.0f, LocalDateTime.parse("2000-01-11T00:00:00"), 175, 'T',
                        0.0f, null, false, null))
                );
    }
}