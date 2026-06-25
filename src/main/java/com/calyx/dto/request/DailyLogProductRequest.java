package com.calyx.dto.request;

import java.time.LocalDate;

public record DailyLogProductRequest(
        Long userId,
        Long productId,
        double grams,
        LocalDate logDate) {
}
