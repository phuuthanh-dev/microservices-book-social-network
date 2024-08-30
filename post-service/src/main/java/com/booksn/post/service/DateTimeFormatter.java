package com.booksn.post.service;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class DateTimeFormatter {
    // Long la gia tri condition - Tham so dau vao la Instant, tra ve String
    Map<Long, Function<Instant, String>> strategies = new LinkedHashMap<>();

    public DateTimeFormatter() {
        strategies.put(60L, this::formatInSeconds);
        strategies.put(3600L, this::formatInMinutes);
        strategies.put(86400L, this::formatInHours);
        strategies.put(604800L, this::formatInDays);
        strategies.put(Long.MAX_VALUE, this::formatOut7Days);
    }

    public String formatDateTime(Instant instant) {
        long elapseSeconds = ChronoUnit.SECONDS.between(instant, Instant.now());

        var strategy = strategies.entrySet()
                .stream()
                .filter(entry -> elapseSeconds < entry.getKey())
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException("Invalid elapseSeconds: " + elapseSeconds)
                );

        return strategy.getValue().apply(instant);
    }

    private String formatInSeconds(Instant instant) {
        long elapseSeconds = ChronoUnit.SECONDS.between(instant, Instant.now());
        return elapseSeconds + " seconds ago";
    }

    private String formatInMinutes(Instant instant) {
        long elapseMinutes = ChronoUnit.MINUTES.between(instant, Instant.now());
        return elapseMinutes + " minutes ago";
    }

    private String formatInHours(Instant instant) {
        long elapseHours = ChronoUnit.HOURS.between(instant, Instant.now());
        return elapseHours + " hours ago";
    }

    private String formatInDays(Instant instant) {
        long elapseDays = ChronoUnit.DAYS.between(instant, Instant.now());
        return elapseDays + " days ago";
    }

    private String formatOut7Days(Instant instant) {
        LocalDateTime localDataTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        java.time.format.DateTimeFormatter dateTimeFormatter = java.time.format.DateTimeFormatter.ISO_DATE;
        return localDataTime.format(dateTimeFormatter);
    }
}
