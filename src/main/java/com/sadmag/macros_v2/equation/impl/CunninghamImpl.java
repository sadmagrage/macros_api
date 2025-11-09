package com.sadmag.macros_v2.equation.impl;

import com.sadmag.macros_v2.equation.Equation;
import com.sadmag.macros_v2.equation.exception.MissingValuesInEquation;
import com.sadmag.macros_v2.user_info.UserInfo;
import org.springframework.stereotype.Component;

@Component
public class CunninghamImpl implements Equation {

    @Override
    public float calculate(UserInfo userInfo) {
        var totalKcalSpent = 0.0f;

        if (userInfo.getWeight() == 0.0f) throw new MissingValuesInEquation("Missing weight value");
        if (userInfo.getBodyfat() == 0.0f) throw new MissingValuesInEquation("Missing bodyfat value");

        var weight = userInfo.getWeight();
        var bodyFatPercentage = userInfo.getBodyfat() / 100;

        totalKcalSpent = 21.6f * (weight * (1 - bodyFatPercentage)) + 501.6f;

        return totalKcalSpent;
    }
}