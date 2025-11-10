package com.sadmag.macros_v2.equation.impl;

import com.sadmag.macros_v2.date.DateUtils;
import com.sadmag.macros_v2.equation.Equation;
import com.sadmag.macros_v2.equation.exception.MissingValuesInEquationException;
import com.sadmag.macros_v2.user_info.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MifflinImpl implements Equation {

    @Autowired
    private DateUtils dateUtils;

    public float calculate(UserInfo userInfo) {
        var totalKcalSpent = 0.0f;

        var now = dateUtils.getCurrentTime();

        if (userInfo.getWeight() == 0.0f) throw new MissingValuesInEquationException("Missing weight value");
        if (userInfo.getHeight() == 0.0f) throw new MissingValuesInEquationException("Missing height value");
        if (userInfo.getBirth().isEqual(LocalDateTime.MIN)) throw new MissingValuesInEquationException("Missing birth value");
        if (Character.toUpperCase(userInfo.getGender()) != 'M' && Character.toUpperCase(userInfo.getGender()) != 'F') throw new MissingValuesInEquationException("Unrecognized or missing gender");

        var yearToGetDate = dateUtils.hasDateOccurredThisYear(now, userInfo.getBirth()) ? now.getYear()
                : now.getYear() - 1;

        var age = yearToGetDate - userInfo.getBirth().getYear();
        var gender = userInfo.getGender();
        var height = userInfo.getHeight();
        var weight = userInfo.getWeight();

        totalKcalSpent = 10 * weight + 6.25f * height - 5 * age;

        if (Character.toUpperCase(gender) == 'M') {
            totalKcalSpent += 5;
        } else if (Character.toUpperCase(gender) == 'F') {
            totalKcalSpent -= 161;
        }

        return totalKcalSpent;
    }
}