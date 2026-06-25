package com.calyx.util;

import com.calyx.model.Product;

public final class NutritionCalculator {

    private NutritionCalculator() {
    }

    public record Nutrition(int calories, double proteins, double fats, double carbs) {
    }

    public static Nutrition fromProduct(Product product, double grams) {
        double factor = grams / 100.0;
        return new Nutrition(
                (int) Math.round(product.getCaloriesPer100g() * factor),
                product.getProteinsPer100g() * factor,
                product.getFatsPer100g() * factor,
                product.getCarbsPer100g() * factor
        );
    }

    public static Nutrition fromMealTotals(int totalCalories, double totalProteins,
                                           double totalFats, double totalCarbs, double servings) {
        return new Nutrition(
                (int) Math.round(totalCalories * servings),
                totalProteins * servings,
                totalFats * servings,
                totalCarbs * servings
        );
    }
}
