package com.sadmag.macros_v2.equation.impl;

import com.sadmag.macros_v2.date.DateUtils;
import com.sadmag.macros_v2.user_info.UserInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import static org.junit.jupiter.api.Assertions.*;

class MifflinImplTest {

    @Mock
    private DateUtils dateUtils;

    @Autowired
    @InjectMocks
    private MifflinImpl mifflinImpl;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @DisplayName("calculate() should calculate the basal spent from Mifflin equation when birth has occurred")
    @MethodSource("provideUserInfoBirthOccurred")
    void calculate_shouldCalculateTheBasalSpentFromMifflinEquationWhenBirthHasOccurred(UserInfo userInfo, float expectedValue) {
        Mockito.when(dateUtils.getCurrentTime()).thenReturn(LocalDateTime.parse("2025-11-07T16:59:00"));
        Mockito.when(dateUtils.hasDateOccurredThisYear(Mockito.any(), Mockito.any())).thenReturn(true);

        var result = mifflinImpl.calculate(userInfo);

        Assertions.assertTrue(Math.abs(expectedValue - result) < 1);
    }

    @ParameterizedTest
    @DisplayName("calculate() should calculate using Mifflin equation when birth has not occurred yet")
    @MethodSource("provideUserInfoBirthHasNotOccurredYet")
    void calculate_shouldCalculateUsingMifflinEquationWhenBirthHasnotOccurredYet(UserInfo userInfo, float expectedValue) {
        Mockito.when(dateUtils.getCurrentTime()).thenReturn(LocalDateTime.parse("2025-01-10T16:59:00"));
        Mockito.when(dateUtils.hasDateOccurredThisYear(Mockito.any(), Mockito.any())).thenReturn(false);

        var result = mifflinImpl.calculate(userInfo);

        Assertions.assertTrue(Math.abs(expectedValue - result) < 1);
    }

    static Stream<Arguments> provideUserInfoBirthOccurred() {
        return Stream.of(
                Arguments.of(new UserInfo(null, 70.0f, 10.0f, LocalDateTime.parse("2000-01-11T00:00:00"), 175, 'M',
                        0.0f, null, false, null), 1673.75f),
                Arguments.of(new UserInfo(null, 90.0f, 10.0f, LocalDateTime.parse("1985-04-09T00:00:00"), 180, 'M',
                        0.0f, null, false, null), 1830.00f),
                Arguments.of(new UserInfo(null, 60.0f, 10.0f, LocalDateTime.parse("1995-06-30T00:00:00"), 165, 'F',
                        0.0f, null, false, null), 1320.25f),
                Arguments.of(new UserInfo(null, 70.0f, 10.0f, LocalDateTime.parse("1975-12-31T00:00:00"), 160, 'F',
                        0.0f, null, false, null), 1289.00f));
    }

    static Stream<Arguments> provideUserInfoBirthHasNotOccurredYet() {
        return Stream.of(
                Arguments.of(new UserInfo(null, 70.0f, 10.0f, LocalDateTime.parse("2000-01-11T00:00:00"), 175, 'M',
                        0.0f, null, false, null), 1678.75f),
                Arguments.of(new UserInfo(null, 90.0f, 10.0f, LocalDateTime.parse("1985-04-09T00:00:00"), 180, 'M',
                        0.0f, null, false, null), 1835.00f),
                Arguments.of(new UserInfo(null, 60.0f, 10.0f, LocalDateTime.parse("1995-06-30T00:00:00"), 165, 'F',
                        0.0f, null, false, null), 1325.25f),
                Arguments.of(new UserInfo(null, 70.0f, 10.0f, LocalDateTime.parse("1975-12-31T00:00:00"), 160, 'F',
                        0.0f, null, false, null), 1294.00f));
    }
}