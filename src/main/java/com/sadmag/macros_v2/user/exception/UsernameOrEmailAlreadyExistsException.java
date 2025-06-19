package com.sadmag.macros_v2.user.exception;

public class UsernameOrEmailAlreadyExistsException extends RuntimeException {
    public UsernameOrEmailAlreadyExistsException(String message) { super(message); }
}