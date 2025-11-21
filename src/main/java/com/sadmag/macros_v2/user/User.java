package com.sadmag.macros_v2.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sadmag.macros_v2.user_info.UserInfo;
import com.sadmag.macros_v2.user_preference.UserPreference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user_table")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements UserDetails {

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

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true, optional = false)
    @JsonIgnoreProperties("user")
    private UserPreference userPreference;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.userRole == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}