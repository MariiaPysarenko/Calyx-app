package com.calyx.util;

import com.calyx.model.Product;
import com.calyx.testutil.TestData;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NutritionCalculatorTest {

    @Test
    public void fromProduct_calculatesNutritionForGivenGrams() {
        Product product = TestData.sampleProduct();

        NutritionCalculator.Nutrition nutrition = NutritionCalculator.fromProduct(product, 100);

        assertEquals(165, nutrition.calories());
        assertEquals(31.0, nutrition.proteins(), 0.001);
        assertEquals(3.6, nutrition.fats(), 0.001);
        assertEquals(0.0, nutrition.carbs(), 0.001);
    }

    @Test
    public void fromProduct_roundsCalories() {
        Product product = new Product(2L, "Banana", "fruits", 89, 1.1, 0.3, 22.8);

        NutritionCalculator.Nutrition nutrition = NutritionCalculator.fromProduct(product, 50);

        assertEquals(45, nutrition.calories());
        assertEquals(0.55, nutrition.proteins(), 0.001);
    }

    @Test
    public void fromMealTotals_scalesByServings() {
        NutritionCalculator.Nutrition nutrition = NutritionCalculator.fromMealTotals(
                482, 50.0, 10.0, 45.0, 2.0
        );

        assertEquals(964, nutrition.calories());
        assertEquals(100.0, nutrition.proteins(), 0.001);
        assertEquals(20.0, nutrition.fats(), 0.001);
        assertEquals(90.0, nutrition.carbs(), 0.001);
    }
}
