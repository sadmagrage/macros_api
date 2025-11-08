package com.sadmag.macros_v2.equation.impl;

import com.sadmag.macros_v2.date.DateUtils;
import com.sadmag.macros_v2.equation.Equation;
import com.sadmag.macros_v2.equation.exception.MissingValuesInEquation;
import com.sadmag.macros_v2.user_info.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class HarrisBenedictEquationImpl implements Equation {

    @Autowired
    private DateUtils dateUtils;

    public float calculate(UserInfo userInfo) {
        var totalKcalSpent = 0.0f;

        var now = dateUtils.getCurrentTime();

        var yearToGetDate = dateUtils.hasDateOccurredThisYear(now, userInfo.getBirth()) ? now.getYear()
                : now.getYear() - 1;

        var age = yearToGetDate - userInfo.getBirth().getYear();
        var gender = userInfo.getGender();
        var height = userInfo.getHeight();
        var weight = userInfo.getWeight();

        if (Character.toUpperCase(gender) == 'M') {
            totalKcalSpent = 66.47f + (13.75f * weight) + (5.003f * height) - (6.775f * age);
        } else if (Character.toUpperCase(gender) == 'F') {
            totalKcalSpent = 655.09f + (9.563f * weight) + (1.85f * height) - (4.676f * age);
        } else {
            throw new MissingValuesInEquation("Unrecognized or missing gender");
        }

        return totalKcalSpent;
    }
}