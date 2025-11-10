package com.sadmag.macros_v2.equation;

import com.sadmag.macros_v2.equation.exception.EquationNotFoundException;
import com.sadmag.macros_v2.user_info.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EquationService {

    @Autowired
    private Map<String, Equation> equations;

    public float calculate(UserInfo userInfo) {
        var totalSpent = 0.0f;

        if (userInfo.getEquationPreference() == null) throw new EquationNotFoundException("No equation preference was found");

        var userEquationPreference = userInfo.getEquationPreference().getValueImpl();

        if (!equations.containsKey(userEquationPreference)) throw new EquationNotFoundException("Equation not found.");

        var equationPreference = equations.get(userEquationPreference);

        var basalSpent = equationPreference.calculate(userInfo);

        totalSpent = basalSpent * userInfo.getActivityFactor();

        return totalSpent;
    }
}