package com.sadmag.macros_v2.profile;

import com.sadmag.macros_v2.equation.EquationPreference;
import com.sadmag.macros_v2.phase.PhaseEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponse {

    private UUID id;
    private float weight;
    private float bodyfat;
    private LocalDateTime birth;
    private int height;
    private char gender;
    private float activityFactor;
    private EquationPreference equationPreference;
    private boolean macroInfoPublic;
    private boolean profileActive;
    private PhaseEnum phase;
    private float superavitPercentage;
    private float deficitValue;
}