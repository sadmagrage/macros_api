package com.sadmag.macros_v2.validator;

public interface Validator<T> {
    void validate(T t);
}