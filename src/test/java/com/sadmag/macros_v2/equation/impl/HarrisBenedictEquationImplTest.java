package com.sadmag.macros_v2.equation.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HarrisBenedictEquationImplTest {

    @Autowired
    @InjectMocks
    private HarrisBenedictEquationImpl harrisBenedictEquation;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("calculate() should calculate the basal spent from Harris Bennedict formula")
    void shouldCalculateUsingHarrisBennedictFormula() {

    }
}