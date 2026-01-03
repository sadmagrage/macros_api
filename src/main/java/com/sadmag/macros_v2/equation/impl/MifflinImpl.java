package com.sadmag.macros_v2.equation.impl;

import com.sadmag.macros_v2.date.DateUtils;
import com.sadmag.macros_v2.equation.Equation;
import com.sadmag.macros_v2.equation.exception.MissingValuesInEquationException;
import com.sadmag.macros_v2.profile.ProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MifflinImpl implements Equation {

    @Autowired
    private DateUtils dateUtils;

    public float calculate(ProfileResponse profile) {
        var totalKcalSpent = 0.0f;

        var now = dateUtils.getCurrentTime();

        if (profile.getWeight() == 0.0f) throw new MissingValuesInEquationException("Missing weight value");
        if (profile.getHeight() == 0.0f) throw new MissingValuesInEquationException("Missing height value");
        if (profile.getBirth().isEqual(LocalDateTime.MIN)) throw new MissingValuesInEquationException("Missing birth value");
        if (Character.toUpperCase(profile.getGender()) != 'M' && Character.toUpperCase(profile.getGender()) != 'F') throw new MissingValuesInEquationException("Unrecognized or missing gender");

        var yearToGetDate = dateUtils.hasDateOccurredThisYear(now, profile.getBirth()) ? now.getYear()
                : now.getYear() - 1;

        var age = yearToGetDate - profile.getBirth().getYear();
        var gender = profile.getGender();
        var height = profile.getHeight();
        var weight = profile.getWeight();

        totalKcalSpent = 10 * weight + 6.25f * height - 5 * age;

        if (Character.toUpperCase(gender) == 'M') {
            totalKcalSpent += 5;
        } else if (Character.toUpperCase(gender) == 'F') {
            totalKcalSpent -= 161;
        }

        return totalKcalSpent;
    }
}