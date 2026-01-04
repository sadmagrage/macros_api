package com.sadmag.macros_v2.equation;

import com.sadmag.macros_v2.equation.exception.EquationNotFoundException;
import com.sadmag.macros_v2.profile.ProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EquationService {

    @Autowired
    private Map<String, Equation> equations;

    public float calculate(ProfileResponse profile) {
        var totalSpent = 0.0f;

        if (profile.getEquationPreference() == null) throw new EquationNotFoundException("No equation preference was found");

        var userEquationPreference = profile.getEquationPreference().getValueImpl();

        if (!equations.containsKey(userEquationPreference)) throw new EquationNotFoundException("Equation not found.");

        var equationPreference = equations.get(userEquationPreference);

        var basalSpent = equationPreference.calculate(profile);

        totalSpent = basalSpent * profile.getActivityFactor();

        return totalSpent;
    }
}