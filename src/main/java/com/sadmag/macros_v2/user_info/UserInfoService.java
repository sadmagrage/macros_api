package com.sadmag.macros_v2.user_info;

import com.sadmag.macros_v2.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {

    @Autowired
    private UserService userService;

    public UserInfo findUserInfoByUsername(String username) {
        return userService.findUserByUsername(username).getUserInfo();
    }
}