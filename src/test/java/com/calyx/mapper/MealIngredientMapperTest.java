package com.calyx.mapper;

import com.calyx.dto.response.MealIngredientResponse;
import com.calyx.testutil.TestData;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MealIngredientMapperTest {

    @Test
    public void toResponse_mapsIngredient() {
        MealIngredientResponse response = MealIngredientMapper.toResponse(TestData.sampleIngredient());

        assertEquals(Long.valueOf(1L), response.productId());
        assertEquals(150.0, response.grams(), 0.001);
        assertEquals(248, response.calories());
    }
}
