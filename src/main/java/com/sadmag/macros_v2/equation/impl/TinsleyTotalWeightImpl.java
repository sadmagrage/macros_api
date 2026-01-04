package com.sadmag.macros_v2.equation.impl;

import com.sadmag.macros_v2.equation.Equation;
import com.sadmag.macros_v2.equation.exception.MissingValuesInEquationException;
import com.sadmag.macros_v2.profile.ProfileResponse;
import org.springframework.stereotype.Component;

@Component
public class TinsleyTotalWeightImpl implements Equation {

    @Override
    public float calculate(ProfileResponse profile) {
        var totalKcalSpent = 0.0f;

        if (profile.getWeight() == 0.0f) throw new MissingValuesInEquationException("Missing weight value");

        var weight = profile.getWeight();

        totalKcalSpent = 24.8f * weight + 10;

        return totalKcalSpent;
    }
}