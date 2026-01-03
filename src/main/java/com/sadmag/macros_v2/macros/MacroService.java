package com.sadmag.macros_v2.macros;

import com.sadmag.macros_v2.equation.EquationService;
import com.sadmag.macros_v2.profile.exceptions.ProfileActiveNotFoundException;
import com.sadmag.macros_v2.profile.ProfileResponse;
import com.sadmag.macros_v2.profile.ProfileService;
import com.sadmag.macros_v2.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MacroService {

    @Autowired
    private EquationService equationService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private TokenService tokenService;

    public MacroDto calculate(String token) {
        float carb, prot, fat, caloricTarget;

        var profiles = profileService.findAllByAuthenticatedUser(token);

        var activeProfile = profiles.stream().filter(ProfileResponse::isProfileActive).findFirst().orElseThrow(ProfileActiveNotFoundException::new);

        var tdee = equationService.calculate(activeProfile);

        caloricTarget = switch (activeProfile.getPhase()) {
            case BULKING -> tdee * (1 + (activeProfile.getSuperavitPercentage() / 100));
            case CUTTING -> tdee - activeProfile.getDeficitValue();
            default -> tdee;
        };

        prot = activeProfile.getWeight() * 2;
        fat = activeProfile.getWeight();
        carb = (caloricTarget - (fat * 9 + prot * 4))/4;

        return new MacroDto(carb, prot, fat, caloricTarget);
    }
}