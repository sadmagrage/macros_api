package com.sadmag.macros_v2.user_preference;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sadmag.macros_v2.phase.PhaseEnum;
import com.sadmag.macros_v2.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "tb_user_preference")
public class UserPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private PhaseEnum phase;

    @Column
    private float superavitPercentage;

    @Column
    private float deficitValue;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonIgnoreProperties("user_preference")
    private User user;
}