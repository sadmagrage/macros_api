package com.sadmag.macros_v2.user.validators.impl;

import com.sadmag.macros_v2.user.UserDto;
import com.sadmag.macros_v2.user.exception.ValidationException;
import com.sadmag.macros_v2.user.validators.Validator;
import org.springframework.stereotype.Service;

@Service
public class EmailValidator implements Validator<UserDto> {
    @Override
    public void validate(UserDto userDto) {
        var email = userDto.getEmail();
        String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        if (email == null || !email.matches(regex)) throw new ValidationException("Invalid email");
    }
}