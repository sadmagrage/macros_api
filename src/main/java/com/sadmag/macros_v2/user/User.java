package com.sadmag.macros_v2.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sadmag.macros_v2.user_info.UserInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "user_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private UserRole userRole;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true, optional = false)
    @JsonIgnoreProperties("user")
    private UserInfo userInfo;
}