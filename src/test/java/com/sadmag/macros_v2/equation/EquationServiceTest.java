package com.sadmag.macros_v2.equation;

import com.sadmag.macros_v2.date.DateUtils;
import com.sadmag.macros_v2.equation.exception.EquationNotFound;
import com.sadmag.macros_v2.equation.impl.*;
import com.sadmag.macros_v2.user.UserDto;
import com.sadmag.macros_v2.user.UserRepository;
import com.sadmag.macros_v2.user.validators.Validator;
import com.sadmag.macros_v2.user.validators.impl.EmailValidator;
import com.sadmag.macros_v2.user.validators.impl.GenderValidator;
import com.sadmag.macros_v2.user.validators.impl.PasswordValidator;
import com.sadmag.macros_v2.user_info.UserInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

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
        var userInfo = new UserInfo(null, 70.0f, 10.0f, LocalDateTime.parse("2000-01-11T00:00:00"), 175, 'M',1.5f, EquationPreference.HARRIS_BENEDICT, false, null);

        Mockito.when(harrisBenedictImpl.calculate(Mockito.any())).thenReturn(2000.0f);

        var result = equationService.calculate(userInfo);

        Assertions.assertEquals(3000.0f, result);
    }

    @Test
    @DisplayName("calculate() should throw EquationNotFound when equationPreference is null")
    void calculate_shouldThrowEquationNotFoundWhenEquationPreferenceIsNull() {
        var userInfo = new UserInfo(null, 70.0f, 10.0f, LocalDateTime.parse("2000-01-11T00:00:00"), 175, 'M',1.5f, null, false, null);

        Assertions.assertThrows(EquationNotFound.class, () -> equationService.calculate(userInfo));
    }

    @Test
    @DisplayName("calculate() should throw EquationNotFound when equationPreference is invalid")
    void calculate_shouldThrowEquationNotFoundWhenEquationPreferenceIsInvalid() {
        var userInfo = new UserInfo(null, 70.0f, 10.0f, LocalDateTime.parse("2000-01-11T00:00:00"), 175, 'M',1.5f, EquationPreference.TINSLEY_TOTAL_WEIGHT, false, null);

        Assertions.assertThrows(EquationNotFound.class, () -> equationService.calculate(userInfo));
    }
}