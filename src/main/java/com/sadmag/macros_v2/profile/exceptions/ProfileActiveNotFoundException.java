package com.sadmag.macros_v2.profile.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProfileActiveNotFoundException extends RuntimeException {
    public ProfileActiveNotFoundException(String message) {
        super(message);
    }
}
