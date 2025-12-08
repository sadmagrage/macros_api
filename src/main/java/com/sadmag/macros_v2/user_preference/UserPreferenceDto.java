package com.sadmag.macros_v2.user_preference;

import com.sadmag.macros_v2.phase.PhaseEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPreferenceDto {
    private PhaseEnum phase;
    private float superavitPercentage;
    private float deficitValue;
}