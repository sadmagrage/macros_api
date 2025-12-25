package com.sadmag.macros_v2.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sadmag.macros_v2.exceptions.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException ex
    ) throws IOException {

        var error = "Unauthorized";
        var messageResponse = "Auth token not provided, invalid or expired";

        var statusCode = (short) HttpStatus.UNAUTHORIZED.value();

        var exceptionResponse = new ExceptionResponse(LocalDateTime.now(), statusCode, error, messageResponse, request.getRequestURI());

        var exceptionResponseStr = new ObjectMapper().writeValueAsString(exceptionResponse);

        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.getWriter().write(exceptionResponseStr);
    }
}