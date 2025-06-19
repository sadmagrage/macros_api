package com.sadmag.macros_v2.user.validators.impl;

import com.sadmag.macros_v2.user.UserDto;
import com.sadmag.macros_v2.user.exception.ValidationException;
import com.sadmag.macros_v2.user.validators.Validator;
import org.springframework.stereotype.Service;

@Service
public class GenderValidator implements Validator<UserDto> {

    @Override
    public void validate(UserDto userDto) {
        var gender = userDto.getUserInfo().getGender();
        if (gender != 'M' && gender != 'F') throw new ValidationException("Gender not recognized, use only \"M\" or \"F\"");
    }
}