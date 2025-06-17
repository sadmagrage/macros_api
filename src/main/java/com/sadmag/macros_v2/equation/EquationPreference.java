package com.sadmag.macros_v2.equation;

import lombok.Getter;

@Getter
public enum EquationPreference {
    HARRIS_BENEDICT("harrisBenedict"),
    TINSLEY_WEIGHT("tinsleyWeight"),
    TINSLEY_MUSCULAR_WEIGHT("tinsleyMuscularWeight"),
    MIFFLIN("mifflin"),
    CUNNINGHAM("cunningham");

    private String equationPreference;

    EquationPreference(String equationPreference) { }

}