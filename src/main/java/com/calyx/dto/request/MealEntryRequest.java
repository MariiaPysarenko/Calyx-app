package com.calyx.dto.request;

public record MealEntryRequest(
        Long mealId,
        Long productId,
        double grams
) {}