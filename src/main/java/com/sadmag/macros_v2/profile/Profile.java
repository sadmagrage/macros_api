package com.sadmag.macros_v2.profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sadmag.macros_v2.equation.EquationPreference;
import com.sadmag.macros_v2.phase.PhaseEnum;
import com.sadmag.macros_v2.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_profile")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

    public Profile(float weight, float bodyfat, LocalDateTime birth, int height, char gender, float activityFactor, EquationPreference equationPreference, boolean macroInfoPublic, PhaseEnum phase, float superavitPercentage, float deficitValue, boolean profileActive, User user) {
        this.weight = weight;
        this.bodyfat = bodyfat;
        this.birth = birth;
        this.height = height;
        this.gender = gender;
        this.activityFactor = activityFactor;
        this.equationPreference = equationPreference;
        this.macroInfoPublic = macroInfoPublic;
        this.phase = phase;
        this.superavitPercentage = superavitPercentage;
        this.deficitValue = deficitValue;
        this.profileActive = profileActive;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    //user info
    @Column
    private float weight;

    @Column
    private float bodyfat;

    @Column
    private LocalDateTime birth;

    @Column
    private int height;

    @Column
    private char gender;

    @Column
    private float activityFactor;

    @Column
    private EquationPreference equationPreference;

    @Column
    private boolean macroInfoPublic;

    //user pref
    @Column
    private PhaseEnum phase;

    @Column
    private float superavitPercentage;

    @Column
    private float deficitValue;

    @Column
    private boolean profileActive;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    @JsonIgnoreProperties("profiles")
    private User user;

}