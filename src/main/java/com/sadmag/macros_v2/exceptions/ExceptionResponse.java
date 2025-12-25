package com.sadmag.macros_v2.exceptions;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sadmag.macros_v2.date.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timestamp;
    private short status;
    private String error;
    private String message;
    private String path;
}