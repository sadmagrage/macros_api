package com.sadmag.macros_v2.user.validators;

public interface Validator<T> {
    void validate(T t);
}