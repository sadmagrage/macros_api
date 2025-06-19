package com.sadmag.macros_v2.user.validators;

public interface Validator<UserDto> {
    void validate(UserDto userDto);
}