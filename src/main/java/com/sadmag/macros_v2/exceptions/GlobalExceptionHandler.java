package com.sadmag.macros_v2.exceptions;

import com.sadmag.macros_v2.equation.exception.EquationNotFoundException;
import com.sadmag.macros_v2.equation.exception.MissingValuesInEquationException;
import com.sadmag.macros_v2.user.exception.UserNotFoundException;
import com.sadmag.macros_v2.user.exception.UsernameOrEmailAlreadyExistsException;
import com.sadmag.macros_v2.user.exception.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, HttpServletRequest req) {
        var error = "User not found";
        var statusCode = (short) HttpStatus.NOT_FOUND.value();

        var exceptionResponse = new ExceptionResponse(LocalDateTime.now(), statusCode, error, ex.getMessage(), req.getRequestURI());

        return ResponseEntity.status(statusCode).body(exceptionResponse);
    }

    @ExceptionHandler(UsernameOrEmailAlreadyExistsException.class)
    public ResponseEntity<Object> handleUsernameOrEmailAlreadyExistsException(UsernameOrEmailAlreadyExistsException ex, HttpServletRequest req) {
        var error = "User or email already exists";
        var statusCode = (short) HttpStatus.BAD_REQUEST.value();

        var exceptionResponse = new ExceptionResponse(LocalDateTime.now(), statusCode, error, ex.getMessage(), req.getRequestURI());

        return ResponseEntity.status(statusCode).body(exceptionResponse);
    }

    @ExceptionHandler(MissingValuesInEquationException.class)
    public ResponseEntity<Object> handleMissingValuesInEquationExceptionException(MissingValuesInEquationException ex, HttpServletRequest req) {
        var error = "Missing values in equation";
        var statusCode = (short) HttpStatus.BAD_REQUEST.value();

        var exceptionResponse = new ExceptionResponse(LocalDateTime.now(), statusCode, error, ex.getMessage(), req.getRequestURI());

        return ResponseEntity.status(statusCode).body(exceptionResponse);
    }

    @ExceptionHandler(EquationNotFoundException.class)
    public ResponseEntity<Object> handleEquationNotFoundExceptionException(EquationNotFoundException ex, HttpServletRequest req) {
        var error = "Equation not found";
        var statusCode = (short) HttpStatus.NOT_FOUND.value();

        var exceptionResponse = new ExceptionResponse(LocalDateTime.now(), statusCode, error, ex.getMessage(), req.getRequestURI());

        return ResponseEntity.status(statusCode).body(exceptionResponse);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationExceptionException(ValidationException ex, HttpServletRequest req) {
        var error = "Fields not valid";
        var statusCode = (short) HttpStatus.BAD_REQUEST.value();

        var exceptionResponse = new ExceptionResponse(LocalDateTime.now(), statusCode, error, ex.getMessage(), req.getRequestURI());

        return ResponseEntity.status(statusCode).body(exceptionResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleInvalidException(MethodArgumentTypeMismatchException ex, HttpServletRequest req) {
        var error = "Invalid request body";
        var statusCode = (short) HttpStatus.BAD_REQUEST.value();

        var exceptionResponse = new ExceptionResponse(LocalDateTime.now(), statusCode, error, ex.getMessage(), req.getRequestURI());

        return ResponseEntity.status(statusCode).body(exceptionResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex, HttpServletRequest req) {
        var error = "An error occurred";
        var statusCode = (short) HttpStatus.INTERNAL_SERVER_ERROR.value();

        var exceptionResponse = new ExceptionResponse(LocalDateTime.now(), statusCode, error, ex.getMessage(), req.getRequestURI());

        return ResponseEntity.status(statusCode).body(exceptionResponse);
    }
}