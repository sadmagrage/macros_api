package com.sadmag.macros_v2.equation.impl;

import com.sadmag.macros_v2.equation.Equation;
import com.sadmag.macros_v2.equation.exception.MissingValuesInEquation;
import com.sadmag.macros_v2.user_info.UserInfo;
import org.springframework.stereotype.Component;

@Component
public class TinsleyTotalWeightImpl implements Equation {

    @Override
    public float calculate(UserInfo userInfo) {
        var totalKcalSpent = 0.0f;

        if (userInfo.getWeight() == 0.0f) throw new MissingValuesInEquation("Missing weight value");

        var weight = userInfo.getWeight();

        totalKcalSpent = 24.8f * weight + 10;

        return totalKcalSpent;
    }
}