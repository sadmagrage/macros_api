package com.sadmag.macros_v2.macros;

import com.sadmag.macros_v2.equation.EquationService;
import com.sadmag.macros_v2.phase.PhaseEnum;
import com.sadmag.macros_v2.token.TokenService;
import com.sadmag.macros_v2.user_info.UserInfo;
import com.sadmag.macros_v2.user_info.UserInfoService;
import com.sadmag.macros_v2.user_preference.UserPreference;
import com.sadmag.macros_v2.user_preference.UserPreferenceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.*;

@Service
class MacroServiceTest {

    @Mock
    private EquationService equationService;

    @Mock
    private UserInfoService userInfoService;

    @Mock
    private UserPreferenceService userPreferenceService;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    @Autowired
    private MacroService macroService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("calculate() should return the MacrosDto when phase is set in Bulking")
    void calculate_shouldReturnTheMacrosDtoWhenPhaseIsSetInBulking() {
        var userInfo = new UserInfo();
        var userPreference = new UserPreference();

        userInfo.setWeight(80.0f);

        userPreference.setPhase(PhaseEnum.BULKING);
        userPreference.setSuperavitPercentage(15.0f);

        Mockito.when(tokenService.validateToken(Mockito.any())).thenReturn("teste");
        Mockito.when(userInfoService.findUserInfoByUsername(Mockito.any())).thenReturn(userInfo);
        Mockito.when(userPreferenceService.findUserPreferenceByUsername(Mockito.any())).thenReturn(userPreference);
        Mockito.when(equationService.calculate(Mockito.any())).thenReturn(3170.06f);

        var result = macroService.calculate("token");

        Assertions.assertTrue(Math.abs(160.0f - result.getProt()) < 1);
        Assertions.assertTrue(Math.abs(80.0f - result.getFat()) < 1);
        Assertions.assertTrue(Math.abs(571.39225f - result.getCarb()) < 1);
        Assertions.assertTrue(Math.abs(3645.569f - result.getTotalSpent()) < 1);
    }

    @Test
    @DisplayName("calculate() should return the MacrosDto when phase is set in Maintenance")
    void calculate_shouldReturnTheMacrosDtoWhenPhaseIsSetInMaintenance() {
        var userInfo = new UserInfo();
        var userPreference = new UserPreference();

        userInfo.setWeight(80.0f);

        userPreference.setPhase(PhaseEnum.MAINTENANCE);

        Mockito.when(tokenService.validateToken(Mockito.any())).thenReturn("teste");
        Mockito.when(userInfoService.findUserInfoByUsername(Mockito.any())).thenReturn(userInfo);
        Mockito.when(userPreferenceService.findUserPreferenceByUsername(Mockito.any())).thenReturn(userPreference);
        Mockito.when(equationService.calculate(Mockito.any())).thenReturn(3170.06f);

        var result = macroService.calculate("token");

        Assertions.assertTrue(Math.abs(160.0f - result.getProt()) < 1);
        Assertions.assertTrue(Math.abs(80.0f - result.getFat()) < 1);
        Assertions.assertTrue(Math.abs(452.515f - result.getCarb()) < 1);
        Assertions.assertTrue(Math.abs(3170.06f - result.getTotalSpent()) < 1);
    }

    @Test
    @DisplayName("calculate() should return the MacrosDto when phase is set in Cutting")
    void calculate_shouldReturnTheMacrosDtoWhenPhaseIsSetInCutting() {
        var userInfo = new UserInfo();
        var userPreference = new UserPreference();

        userInfo.setWeight(80.0f);

        userPreference.setPhase(PhaseEnum.CUTTING);
        userPreference.setDeficitValue(500.0f);

        Mockito.when(tokenService.validateToken(Mockito.any())).thenReturn("teste");
        Mockito.when(userInfoService.findUserInfoByUsername(Mockito.any())).thenReturn(userInfo);
        Mockito.when(userPreferenceService.findUserPreferenceByUsername(Mockito.any())).thenReturn(userPreference);
        Mockito.when(equationService.calculate(Mockito.any())).thenReturn(3170.06f);

        var result = macroService.calculate("token");

        Assertions.assertTrue(Math.abs(160.0f - result.getProt()) < 1);
        Assertions.assertTrue(Math.abs(80.0f - result.getFat()) < 1);
        Assertions.assertTrue(Math.abs(327.515f - result.getCarb()) < 1);
        Assertions.assertTrue(Math.abs(2670.06f - result.getTotalSpent()) < 1);
    }
}