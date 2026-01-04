package com.sadmag.macros_v2.profile.exceptions;

public class MaximumProfilesByUserException extends RuntimeException {
    public MaximumProfilesByUserException(String message) {
        super(message);
    }
}
