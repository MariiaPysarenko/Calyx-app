package com.calyx.mapper;

import com.calyx.dto.request.MealRequest;
import com.calyx.dto.response.MealResponse;
import com.calyx.model.Meal;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MealMapperTest {

    @Test
    public void toEntity_createsMealWithZeroTotals() {
        Meal meal = MealMapper.toEntity(new MealRequest(1L, "Pasta"));

        assertEquals(Long.valueOf(1L), meal.getUserId());
        assertEquals("Pasta", meal.getName());
        assertEquals(0, meal.getTotalCalories());
    }

    @Test
    public void toResponse_mapsAllFields() {
        Meal meal = new Meal(5L, 1L, "Salad", 120, 8.0, 4.0, 10.0);

        MealResponse response = MealMapper.toResponse(meal);

        assertEquals(Long.valueOf(5L), response.id());
        assertEquals("Salad", response.name());
        assertEquals(120, response.totalCalories());
    }
}
