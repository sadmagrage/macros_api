package com.sadmag.macros_v2.macros;

import com.sadmag.macros_v2.equation.EquationService;
import com.sadmag.macros_v2.token.TokenService;
import com.sadmag.macros_v2.user_info.UserInfoService;
import com.sadmag.macros_v2.user_preference.UserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MacroService {

    @Autowired
    private EquationService equationService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserPreferenceService userPreferenceService;

    @Autowired
    private TokenService tokenService;

    public MacroDto calculate(String token) {
        float carb, prot, fat, caloricTarget;

        var username = tokenService.validateToken(token);

        var userInfo = userInfoService.findUserInfoByUsername(username);
        var userPreference = userPreferenceService.findUserPreferenceByUsername(username);

        var tdee = equationService.calculate(userInfo);

        caloricTarget = switch (userPreference.getPhase()) {
            case BULKING -> tdee * userPreference.getSuperavitPercentage();
            case CUTTING -> tdee - userPreference.getDeficitValue();
            default -> tdee;
        };

        prot = userInfo.getWeight() / 2;
        fat = userInfo.getWeight();
        carb = (caloricTarget - (fat * 9 + prot * 4))/4;

        return new MacroDto(carb, prot, fat, caloricTarget);
    }
}