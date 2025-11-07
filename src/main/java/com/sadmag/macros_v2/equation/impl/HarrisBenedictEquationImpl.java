package com.sadmag.macros_v2.equation.impl;

import com.sadmag.macros_v2.equation.Equation;
import com.sadmag.macros_v2.equation.exception.MissingValuesInEquation;
import com.sadmag.macros_v2.user_info.UserInfo;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class HarrisBenedictEquationImpl implements Equation {

    public float calculate(UserInfo userInfo) {
        var totalKcalSpent = 0.0f;

        var now = LocalDateTime.now();
        var yearToGetDate = userInfo.checkIfBirthHasOccuredThisYear() ? now.getYear() : now.getYear() - 1;

        var age = yearToGetDate - userInfo.getBirth().getYear();
        var gender = userInfo.getGender();
        var height = userInfo.getHeight();
        var weight = userInfo.getWeight();

        if (Character.toUpperCase(gender) == 'M') {
            totalKcalSpent = 66 + (13.7f * weight) + (5 * height) - (6.8f * age);
        }
        else if (Character.toUpperCase(gender) == 'F') {
            totalKcalSpent = 655 + (9.7f * weight) + (1.8f * height) - (4.7f * age);
        }
        else {
            throw new MissingValuesInEquation("Unrecognized or missing gender");
        }

        return totalKcalSpent;
    }
}