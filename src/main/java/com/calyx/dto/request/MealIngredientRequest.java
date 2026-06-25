package com.calyx.dto.request;

public record MealIngredientRequest(
        Long mealId,
        Long productId,
        double grams) {
}
