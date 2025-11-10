package com.sadmag.macros_v2.equation;

import lombok.Getter;

@Getter
public enum EquationPreference {
    HARRIS_BENEDICT("harrisBenedict"),
    TINSLEY_TOTAL_WEIGHT("tinsleyTotalWeight"),
    TINSLEY_MUSCULAR_WEIGHT("tinsleyMuscularWeight"),
    MIFFLIN("mifflin"),
    CUNNINGHAM("cunningham");

    private String value;

    EquationPreference(String equationPreference) {
        this.value = equationPreference;
    }

    public String getValueImpl() {
        return this.getValue() + "Impl";
    }
}