package com.sadmag.macros_v2.user.validators.impl;

import com.sadmag.macros_v2.user.UserDto;
import com.sadmag.macros_v2.user.exception.ValidationException;
import com.sadmag.macros_v2.user.validators.Validator;
import org.springframework.stereotype.Service;

@Service
public class PasswordValidator implements Validator<UserDto> {
    @Override
    public void validate(UserDto userDto) {
        var password = userDto.getPassword();

        if (password == null || password.length() < 8) throw new ValidationException("Password does not meet the requirements");
        if (!(
                password.matches(".*[A-Z].*") &&
                        password.matches(".*[a-z].*") &&
                        password.matches(".*\\d.*") &&
                        password.matches(".*[^A-Za-z0-9].*")
        )) throw new ValidationException("Password does not meet the requirements");
    }
}