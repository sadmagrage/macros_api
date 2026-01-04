package com.sadmag.macros_v2.equation;

import com.sadmag.macros_v2.equation.exception.EquationNotFoundException;
import com.sadmag.macros_v2.equation.impl.*;
import com.sadmag.macros_v2.phase.PhaseEnum;
import com.sadmag.macros_v2.profile.ProfileResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

class EquationServiceTest {

    @Mock
    private HarrisBenedictImpl harrisBenedictImpl;

    @Mock
    private TinsleyTotalWeightImpl tinsleyTotalWeightImpl;

    @Spy
    private Map<String, Equation> equations = new HashMap<>();

    @Autowired
    @InjectMocks
    private EquationService equationService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        equations.put("harrisBenedictImpl", harrisBenedictImpl);
        equations.put("tinsleyTotalWeightWrongImpl", tinsleyTotalWeightImpl);
    }

    @Test
    @DisplayName("calculate() should do the basal value times activity factor")
    void calculate_shouldDoTheBasalValueTimesActivityFactor() {
        var profile = new ProfileResponse(null, 70.0f, 10.0f, LocalDateTime.parse("2000-01-11T00:00:00"), 175, 'M',1.5f, EquationPreference.HARRIS_BENEDICT, false, true, PhaseEnum.MAINTENANCE, 15f, 500f);

        Mockito.when(harrisBenedictImpl.calculate(Mockito.any())).thenReturn(2000.0f);

        var result = equationService.calculate(profile);

        Assertions.assertEquals(3000.0f, result);
    }

    @Test
    @DisplayName("calculate() should throw EquationNotFound when equationPreference is null")
    void calculate_shouldThrowEquationNotFoundWhenEquationPreferenceIsNull() {
        var profile = new ProfileResponse(null, 70.0f, 10.0f, LocalDateTime.parse("2000-01-11T00:00:00"), 175, 'M',1.5f, null, false, true, PhaseEnum.MAINTENANCE, 15f, 500f);

        Assertions.assertThrows(EquationNotFoundException.class, () -> equationService.calculate(profile));
    }

    @Test
    @DisplayName("calculate() should throw EquationNotFound when equationPreference is invalid")
    void calculate_shouldThrowEquationNotFoundWhenEquationPreferenceIsInvalid() {
        var profile = new ProfileResponse(null, 70.0f, 10.0f, LocalDateTime.parse("2000-01-11T00:00:00"), 175, 'M',1.5f, EquationPreference.TINSLEY_TOTAL_WEIGHT, false, true, PhaseEnum.MAINTENANCE, 15f, 500f);

        Assertions.assertThrows(EquationNotFoundException.class, () -> equationService.calculate(profile));
    }
}