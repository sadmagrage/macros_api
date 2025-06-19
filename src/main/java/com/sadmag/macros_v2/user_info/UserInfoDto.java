package com.sadmag.macros_v2.user_info;

import com.sadmag.macros_v2.equation.EquationPreference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private float weight;
    private float bodyfat;
    private LocalDateTime birth;
    private int height;
    private char gender;
    private float activityFactor;
    private EquationPreference equationPreference;
    private boolean macroInfoPublic;
}