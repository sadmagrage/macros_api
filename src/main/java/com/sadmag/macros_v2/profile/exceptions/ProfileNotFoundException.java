package com.sadmag.macros_v2.profile.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProfileNotFoundException extends RuntimeException {
    public ProfileNotFoundException(String message) {
        super(message);
    }
}
