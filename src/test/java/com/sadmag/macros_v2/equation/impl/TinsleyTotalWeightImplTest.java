package com.sadmag.macros_v2.equation.impl;

import com.sadmag.macros_v2.equation.exception.MissingValuesInEquation;
import com.sadmag.macros_v2.user_info.UserInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TinsleyTotalWeightImplTest {

    @Autowired
    @InjectMocks
    private TinsleyTotalWeightImpl tinsleyTotalWeight;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @DisplayName("calculate() should calculate using Tinsley total weight equation")
    @MethodSource("provideUserInfo")
    void calculate_shouldCalculateUsingTinsleyTotalWeightEquation(UserInfo userInfo, float expectedValue) {
        var result = tinsleyTotalWeight.calculate(userInfo);

        System.out.println(result);

        Assertions.assertTrue(Math.abs(expectedValue - result) < 1);
    }

    @Test
    @DisplayName("calculate() should throw MissingValuesException when missing weight value")
    void calculate_shouldThrowMissingValuesExceptionWhenMissingWeightValue() {
        var userInfo = new UserInfo(null, 0.0f, 10.0f, LocalDateTime.parse("2000-01-11T00:00:00"), 175, 'M',0.0f, null, false, null);

        Assertions.assertThrows(MissingValuesInEquation.class, () -> tinsleyTotalWeight.calculate(userInfo));
    }

    static Stream<Arguments> provideUserInfo() {
        return Stream.of(
                Arguments.of(new UserInfo(null, 70.0f, 10.0f, LocalDateTime.parse("2000-01-11T00:00:00"), 175, 'M',
                        0.0f, null, false, null), 1746.00f),
                Arguments.of(new UserInfo(null, 90.0f, 10.0f, LocalDateTime.parse("1985-04-09T00:00:00"), 180, 'M',
                        0.0f, null, false, null), 2242.00f),
                Arguments.of(new UserInfo(null, 60.0f, 10.0f, LocalDateTime.parse("1995-06-30T00:00:00"), 165, 'F',
                        0.0f, null, false, null), 1498.00f),
                Arguments.of(new UserInfo(null, 70.0f, 10.0f, LocalDateTime.parse("1975-12-31T00:00:00"), 160, 'F',
                        0.0f, null, false, null), 1746.00f));
    }
}