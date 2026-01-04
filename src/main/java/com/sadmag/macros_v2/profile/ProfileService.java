package com.sadmag.macros_v2.profile;

import com.sadmag.macros_v2.profile.exceptions.MaximumProfilesByUserException;
import com.sadmag.macros_v2.profile.exceptions.ProfileNotFoundException;
import com.sadmag.macros_v2.token.TokenService;
import com.sadmag.macros_v2.user.UserService;
import com.sadmag.macros_v2.user.exception.UserNotFoundException;
import com.sadmag.macros_v2.user.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private List<Validator<ProfileRequest>> validators;

    public List<ProfileResponse> findAllByAuthenticatedUser(String token) {
        var username = tokenService.validateToken(token);

        return profileRepository.findAllByUsername(username).stream().map(prof -> new ProfileResponse(
                prof.getId(),
                prof.getWeight(),
                prof.getBodyfat(),
                prof.getBirth(),
                prof.getHeight(),
                prof.getGender(),
                prof.getActivityFactor(),
                prof.getEquationPreference(),
                prof.isMacroInfoPublic(),
                prof.isProfileActive(),
                prof.getPhase(),
                prof.getSuperavitPercentage(),
                prof.getDeficitValue()
        )).toList();
    }

    public ProfileResponse save(ProfileRequest profileRequest, String token) {
        var username = tokenService.validateToken(token);

        var user = userService.findUserByUsername(username);

        var userProfilesRegistered = profileRepository.countAllProfilesByUser(username);

        if (userProfilesRegistered > 2) throw new MaximumProfilesByUserException("User already has 3 profiles registered");

        validators.forEach(validator -> validator.validate(profileRequest));

        var profile = new Profile(
                profileRequest.getWeight(),
                profileRequest.getBodyfat(),
                profileRequest.getBirth(),
                profileRequest.getHeight(),
                profileRequest.getGender(),
                profileRequest.getActivityFactor(),
                profileRequest.getEquationPreference(),
                profileRequest.isMacroInfoPublic(),
                profileRequest.getPhase(),
                profileRequest.getSuperavitPercentage(),
                profileRequest.getDeficitValue(),
                profileRequest.isProfileActive(),
                user
        );

        profileRepository.save(profile);

        return new ProfileResponse(
                profile.getId(),
                profile.getWeight(),
                profile.getBodyfat(),
                profile.getBirth(),
                profile.getHeight(),
                profile.getGender(),
                profile.getActivityFactor(),
                profile.getEquationPreference(),
                profile.isMacroInfoPublic(),
                profile.isProfileActive(),
                profile.getPhase(),
                profile.getSuperavitPercentage(),
                profile.getDeficitValue()
        );
    }

    public ProfileResponse update(ProfileRequest profileRequest, String token, UUID profileId) {
        var username = tokenService.validateToken(token);

        var user = userService.findUserByUsername(username);

        validators.forEach(validator -> validator.validate(profileRequest));

        var profile = profileRepository.findByIdAndUsername(profileId, username).orElseThrow(ProfileNotFoundException::new);

        profile = new Profile(
                profileRequest.getWeight(),
                profileRequest.getBodyfat(),
                profileRequest.getBirth(),
                profileRequest.getHeight(),
                profileRequest.getGender(),
                profileRequest.getActivityFactor(),
                profileRequest.getEquationPreference(),
                profileRequest.isMacroInfoPublic(),
                profileRequest.getPhase(),
                profileRequest.getSuperavitPercentage(),
                profileRequest.getDeficitValue(),
                profileRequest.isProfileActive(),
                user
        );

        profileRepository.save(profile);

        return new ProfileResponse(
                profile.getId(),
                profile.getWeight(),
                profile.getBodyfat(),
                profile.getBirth(),
                profile.getHeight(),
                profile.getGender(),
                profile.getActivityFactor(),
                profile.getEquationPreference(),
                profile.isMacroInfoPublic(),
                profile.isProfileActive(),
                profile.getPhase(),
                profile.getSuperavitPercentage(),
                profile.getDeficitValue()
        );
    }

    public void delete(String token, UUID profileId) {
        var username = tokenService.validateToken(token);

        userService.findUserByUsername(username);

        var profile = profileRepository.findByIdAndUsername(profileId, username).orElseThrow(ProfileNotFoundException::new);

        profileRepository.delete(profile);
    }
}