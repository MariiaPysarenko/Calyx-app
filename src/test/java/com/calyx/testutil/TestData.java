package com.calyx.testutil;

import com.calyx.model.DailyLogEntry;
import com.calyx.model.Meal;
import com.calyx.model.MealIngredient;
import com.calyx.model.Product;
import com.calyx.model.User;

import java.time.LocalDate;

public final class TestData {

    private TestData() {
    }

    public static Product sampleProduct() {
        return new Product(1L, "Chicken breast", "protein", 165, 31.0, 3.6, 0.0);
    }

    public static User sampleUser() {
        return new User(1L, "Maria", "maria@calyx.local", "hash", 28, 65.0, 168, "lose_weight");
    }

    public static Meal sampleMeal() {
        return new Meal(1L, 1L, "Chicken rice bowl", 482, 50.0, 10.0, 45.0);
    }

    public static MealIngredient sampleIngredient() {
        return new MealIngredient(1L, 1L, 1L, 150.0, 248, 46.5, 5.4, 0.0);
    }

    public static DailyLogEntry sampleProductLogEntry() {
        return new DailyLogEntry(
                1L, 1L, LocalDate.of(2026, 6, 25),
                "PRODUCT", 1L, null, 100.0, null,
                165, 31.0, 3.6, 0.0
        );
    }

    public static DailyLogEntry sampleMealLogEntry() {
        return new DailyLogEntry(
                2L, 1L, LocalDate.of(2026, 6, 25),
                "MEAL", null, 1L, null, 1.0,
                482, 50.0, 10.0, 45.0
        );
    }
}
