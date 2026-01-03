package com.sadmag.macros_v2.profile;

import com.sadmag.macros_v2.phase.PhaseEnum;
import com.sadmag.macros_v2.profile.exceptions.MaximumProfilesByUserException;
import com.sadmag.macros_v2.profile.exceptions.ProfileNotFoundException;
import com.sadmag.macros_v2.profile.validators.GenderValidator;
import com.sadmag.macros_v2.token.TokenService;
import com.sadmag.macros_v2.user.UserService;
import com.sadmag.macros_v2.user.exception.ValidationException;
import com.sadmag.macros_v2.user.validators.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private UserService userService;

    @Mock
    private TokenService tokenService;

    @Mock
    private GenderValidator genderValidator;

    @Spy
    private List<Validator<ProfileRequest>> validators = new ArrayList<>();

    @Autowired
    @InjectMocks
    private ProfileService profileService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        validators.add(genderValidator);
    }

    @ParameterizedTest
    @DisplayName("save() should throw MaximumProfilesByUserException when user already has more than 3 profiles registered")
    @MethodSource("provideProfileRequestTokenAndUserProfilesCount")
    void save_shouldThrowMaximumProfilesByUserExceptionWhenUserAlreadyHasMoreThan3ProfilesRegistered(ProfileRequest profile, String token, int userProfilesCount) {
        Mockito.when(profileRepository.countAllProfilesByUser(Mockito.any())).thenReturn(userProfilesCount);

        Assertions.assertThrows(MaximumProfilesByUserException.class, () -> profileService.save(profile, token));
    }

    @ParameterizedTest
    @DisplayName("update() should throw ProfileNotFoundException when profile is not found by id and username")
    @MethodSource("provideTokenAndId")
    void update_shouldThrowProfileNotFoundExceptionWhenProfileIsNotFoundByIdAndUsername(String token, UUID profileId) {
        Mockito.when(profileRepository.findByIdAndUsername(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(ProfileNotFoundException.class, () -> profileService.update(new ProfileRequest(), token, profileId));
    }

    @ParameterizedTest
    @DisplayName("delete() should throw ProfileNotFoundException when profile is not found by id and username")
    @MethodSource("provideTokenAndId")
    void delete_shouldThrowProfileNotFoundExceptionWhenProfileIsNotFoundByIdAndUsername(String token, UUID profileId) {
        Mockito.when(profileRepository.findByIdAndUsername(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(ProfileNotFoundException.class, () -> profileService.delete(token, profileId));
    }

    static Stream<Arguments> provideProfileRequestTokenAndUserProfilesCount() {
        return Stream.of(
                Arguments.of(new ProfileRequest(), "username", 3),
                Arguments.of(new ProfileRequest(), "username", 4),
                Arguments.of(new ProfileRequest(), "username", 5)
        );
    }

    static Stream<Arguments> provideTokenAndId() {
        return Stream.of(
                Arguments.of("token", UUID.randomUUID())
        );
    }
}