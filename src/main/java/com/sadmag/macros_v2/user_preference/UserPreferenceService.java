package com.sadmag.macros_v2.user_preference;

import com.sadmag.macros_v2.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPreferenceService {

    @Autowired
    private UserService userService;

    public UserPreference findUserPreferenceByUsername(String username) {
        return userService.findUserByUsername(username).getUserPreference();
    }
}