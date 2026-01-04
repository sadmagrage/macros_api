package com.sadmag.macros_v2.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID> {

    @Query("select p from Profile p inner join fetch p.user where p.user.username = ?1")
    List<Profile> findAllByUsername(String username);

    @Query("select p from Profile p inner join fetch p.user where p.id = ?1 and p.user.username = ?2")
    Optional<Profile> findByIdAndUsername(UUID id, String username);

    @Query("select count(p.id) from Profile p inner join p.user where p.user.username = ?1")
    int countAllProfilesByUser(String username);
}