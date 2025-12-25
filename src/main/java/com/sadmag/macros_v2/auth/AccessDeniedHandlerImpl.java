package com.sadmag.macros_v2.auth;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sadmag.macros_v2.exceptions.ExceptionResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDateTime;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        var error = "Access denied";
        var messageResponse = "Current endpoint requires higher permission";

        var statusCode = (short) HttpStatus.FORBIDDEN.value();

        var exceptionResponse = new ExceptionResponse(LocalDateTime.now(), statusCode, error, messageResponse, request.getRequestURI());

        var exceptionResponseStr = new ObjectMapper().writeValueAsString(exceptionResponse);

        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.getWriter().write(exceptionResponseStr);
    }
}
