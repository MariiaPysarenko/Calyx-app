package com.calyx.dto.response;

public record MealResponse(
        Long id,
        Long userId,
        String name,
        int totalCalories,
        double totalProteins,
        double totalFats,
        double totalCarbs) {
}
