package com.sadmag.macros_v2.profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sadmag.macros_v2.equation.EquationPreference;
import com.sadmag.macros_v2.phase.PhaseEnum;
import com.sadmag.macros_v2.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRequest {

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