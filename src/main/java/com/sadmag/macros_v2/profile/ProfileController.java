package com.sadmag.macros_v2.profile;

import com.sadmag.macros_v2.user.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private List<Validator<ProfileRequest>> validators;

    @GetMapping
    public ResponseEntity<Object> findAll(@RequestHeader(name = "Authorization") String authToken) {
        var profiles = profileService.findAllByAuthenticatedUser(authToken);

        return ResponseEntity.ok(profiles);
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestHeader(name = "Authorization") String authToken, @RequestBody ProfileRequest profileRequest) {
        profileService.save(profileRequest, authToken);

        return ResponseEntity.status(201).build();
    }

    @PutMapping(path = "/{profile_id}")
    public ResponseEntity<Object> update(@RequestHeader(name = "Authorization") String authToken, @RequestBody ProfileRequest profileRequest, @PathVariable(name = "profile_id") UUID profileId) {
        var profile = profileService.update(profileRequest, authToken, profileId);

        return ResponseEntity.ok(profile);
    }

    @DeleteMapping(path = "/{profile_id}")
    public ResponseEntity<Object> delete(@RequestHeader(name = "Authorization") String authToken, @PathVariable(name = "profile_id") UUID profileId) {
        profileService.delete(authToken, profileId);

        return ResponseEntity.noContent().build();
    }
}