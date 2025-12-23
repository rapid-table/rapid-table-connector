package com.rapidtable.sdk.rtc4j.resource;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.MINUTES;

public record Credentials(String token, LocalDateTime approvedAt) {
    public boolean isPermitted() {
        if (Objects.isNull(token)) {
            return false;
        }
        final var diff = MINUTES.between(LocalDateTime.now(), approvedAt);
        return Math.abs(diff) <= 50;
    }

    public static Credentials empty() {
        return new Credentials(null, LocalDateTime.MIN);
    }

    public static Credentials approve(String token) {
        return new Credentials(token, LocalDateTime.now());
    }
}