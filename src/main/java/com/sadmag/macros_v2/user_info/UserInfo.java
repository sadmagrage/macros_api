package com.sadmag.macros_v2.user_info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sadmag.macros_v2.equation.EquationPreference;
import com.sadmag.macros_v2.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_user_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

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

    @Column(nullable = false)
    private boolean macroInfoPublic;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonIgnoreProperties("user_info")
    private User user;
}