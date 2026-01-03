package com.sadmag.macros_v2.equation.impl;

import com.sadmag.macros_v2.equation.Equation;
import com.sadmag.macros_v2.equation.exception.MissingValuesInEquationException;
import com.sadmag.macros_v2.profile.ProfileResponse;
import org.springframework.stereotype.Component;

@Component
public class CunninghamImpl implements Equation {

    @Override
    public float calculate(ProfileResponse profile) {
        var totalKcalSpent = 0.0f;

        if (profile.getWeight() == 0.0f) throw new MissingValuesInEquationException("Missing weight value");
        if (profile.getBodyfat() == 0.0f) throw new MissingValuesInEquationException("Missing bodyfat value");

        var weight = profile.getWeight();
        var bodyFatPercentage = profile.getBodyfat() / 100;

        totalKcalSpent = 21.6f * (weight * (1 - bodyFatPercentage)) + 501.6f;

        return totalKcalSpent;
    }
}