package com.calyx.dto.response;

import java.time.LocalDate;

public record DailyCaloriesResponse(
        Long userId,
        LocalDate date,
        int totalCalories) {
}
