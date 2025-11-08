package com.sadmag.macros_v2.date;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class DateUtils {

    public boolean hasDateOccurredThisYear(LocalDateTime referenceDate, LocalDateTime targetDate) {
        return referenceDate.isBefore(targetDate.withYear(referenceDate.getYear()));
    }

    public LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
}