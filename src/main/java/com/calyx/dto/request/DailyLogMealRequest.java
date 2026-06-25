package com.calyx.dto.request;

import java.time.LocalDate;

public record DailyLogMealRequest(
        Long userId,
        Long mealId,
        double servings,
        LocalDate logDate) {
}
