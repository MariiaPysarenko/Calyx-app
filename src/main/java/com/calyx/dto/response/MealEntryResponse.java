package com.calyx.dto.response;

public record MealEntryResponse(
        Long id,
        Long productId,
        double grams,
        int calories
) {}