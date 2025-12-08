package com.sadmag.macros_v2.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("select u from User u where u.username = ?1")
    UserDetails findUserDetailsByUsername(String username);
    @Query("select u from User u inner join fetch u.userInfo ui inner join fetch u.userPreference up where u.username = ?1")
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
}