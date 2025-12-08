package com.sadmag.macros_v2.user;

import com.sadmag.macros_v2.user_info.UserInfoDto;
import com.sadmag.macros_v2.user_preference.UserPreferenceDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;
    private String password;
    private String email;
    private UserInfoDto userInfo;
    private UserPreferenceDto userPreference;
}