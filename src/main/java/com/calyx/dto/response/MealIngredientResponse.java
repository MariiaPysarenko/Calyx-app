package com.calyx.dto.response;

public record MealIngredientResponse(
        Long id,
        Long productId,
        double grams,
        int calories,
        double proteins,
        double fats,
        double carbs) {
}
