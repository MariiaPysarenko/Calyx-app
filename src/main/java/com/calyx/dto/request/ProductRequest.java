package com.calyx.dto.request;

public record ProductRequest(
        String name,
        String category,
        int caloriesPer100g,
        double proteinsPer100g,
        double fatsPer100g,
        double carbsPer100g) {
}
