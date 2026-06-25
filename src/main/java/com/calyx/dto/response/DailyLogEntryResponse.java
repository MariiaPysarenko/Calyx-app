package com.calyx.dto.response;

import java.time.LocalDate;

public record DailyLogEntryResponse(
        Long id,
        LocalDate logDate,
        String entryType,
        String displayName,
        Double grams,
        Double servings,
        int calories,
        double proteins,
        double fats,
        double carbs) {
}
