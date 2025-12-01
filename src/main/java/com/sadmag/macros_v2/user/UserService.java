package com.sadmag.macros_v2.user;

import com.sadmag.macros_v2.user.exception.UserNotFoundException;
import com.sadmag.macros_v2.user.exception.UsernameOrEmailAlreadyExistsException;
import com.sadmag.macros_v2.user.validators.Validator;
import com.sadmag.macros_v2.user_info.UserInfo;
import com.sadmag.macros_v2.user_preference.UserPreference;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private List<Validator<UserDto>> validators;

    @Transactional
    public User saveUser(UserDto userDto) {
        validators.forEach(validator -> validator.validate(userDto));

        var isUsernameAvailable = userRepository.findUserByUsername(userDto.getUsername()).isEmpty();
        var isEmailAvailable = userRepository.findUserByEmail(userDto.getEmail()).isEmpty();

        if (!isUsernameAvailable) throw new UsernameOrEmailAlreadyExistsException("Username already exists");
        if (!isEmailAvailable) throw new UsernameOrEmailAlreadyExistsException("Email already exists");

        var user = new User();
        var userInfo = new UserInfo();
        var userPreference = new UserPreference();
        var hashedPassword = new BCryptPasswordEncoder().encode(userDto.getPassword());

        user.setUsername(userDto.getUsername());
        user.setPassword(hashedPassword);
        user.setEmail(userDto.getEmail());
        user.setUserRole(UserRole.USER);
        user.setUserInfo(userInfo);
        user.setUserPreference(userPreference);

        userInfo.setWeight(userDto.getUserInfo().getWeight());
        userInfo.setBodyfat(userDto.getUserInfo().getBodyfat());
        userInfo.setBirth(userDto.getUserInfo().getBirth());
        userInfo.setHeight(userDto.getUserInfo().getHeight());
        userInfo.setGender(userDto.getUserInfo().getGender());
        userInfo.setActivityFactor(userDto.getUserInfo().getActivityFactor());
        userInfo.setEquationPreference(userDto.getUserInfo().getEquationPreference());
        userInfo.setMacroInfoPublic(userDto.getUserInfo().isMacroInfoPublic());
        userInfo.setUser(user);

        userPreference.setPhase(userDto.getUserPreference().getPhase());
        userPreference.setSuperavitPercentage(userDto.getUserPreference().getSuperavitPercentage());
        userPreference.setDeficitValue(userDto.getUserPreference().getDeficitValue());
        userPreference.setUser(user);

        return userRepository.save(user);
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found."));
    }
}