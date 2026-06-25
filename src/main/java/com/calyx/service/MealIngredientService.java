package com.calyx.service;

import com.calyx.dto.request.MealIngredientRequest;
import com.calyx.dto.response.MealIngredientResponse;

import java.util.List;

public interface MealIngredientService {

    MealIngredientResponse addIngredient(MealIngredientRequest request);

    List<MealIngredientResponse> getIngredientsByMeal(Long mealId);
}
