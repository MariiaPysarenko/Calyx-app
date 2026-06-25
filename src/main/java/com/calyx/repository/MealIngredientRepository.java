package com.calyx.repository;

import com.calyx.model.MealIngredient;

import java.util.List;

public interface MealIngredientRepository {

    MealIngredient save(MealIngredient ingredient);

    List<MealIngredient> findByMealId(Long mealId);

    void deleteById(Long id);
}
