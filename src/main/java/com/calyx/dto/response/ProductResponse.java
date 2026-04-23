package com.calyx.dto.response;

public record ProductResponse(
        Long id,
        String name,
        int caloriesPer100g,
        double proteinsPer100g,
        double fatsPer100g,
        double carbsPer100g) { }
