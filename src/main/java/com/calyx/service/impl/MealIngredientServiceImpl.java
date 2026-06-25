package com.calyx.service.impl;

import com.calyx.dto.request.MealIngredientRequest;
import com.calyx.dto.response.MealIngredientResponse;
import com.calyx.mapper.MealIngredientMapper;
import com.calyx.model.Meal;
import com.calyx.model.MealIngredient;
import com.calyx.model.Product;
import com.calyx.repository.MealIngredientRepository;
import com.calyx.repository.MealRepository;
import com.calyx.repository.ProductRepository;
import com.calyx.service.MealIngredientService;
import com.calyx.util.NutritionCalculator;
import com.calyx.util.Validator;

import java.util.List;

public class MealIngredientServiceImpl implements MealIngredientService {

    private final MealIngredientRepository mealIngredientRepository;
    private final MealRepository mealRepository;
    private final ProductRepository productRepository;

    public MealIngredientServiceImpl(MealIngredientRepository mealIngredientRepository,
                                     MealRepository mealRepository,
                                     ProductRepository productRepository) {
        this.mealIngredientRepository = mealIngredientRepository;
        this.mealRepository = mealRepository;
        this.productRepository = productRepository;
    }

    private void validateRequest(MealIngredientRequest request) {
        Validator.requireValidId(request.mealId(), "meal id");
        Validator.requireValidId(request.productId(), "product id");
        Validator.requirePositive(request.grams(), "Grams");
    }

    private void recalculateMealTotals(Long mealId) {
        List<MealIngredient> ingredients = mealIngredientRepository.findByMealId(mealId);
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("Meal not found"));

        int calories = 0;
        double proteins = 0;
        double fats = 0;
        double carbs = 0;

        for (MealIngredient ingredient : ingredients) {
            calories += ingredient.getCalories();
            proteins += ingredient.getProteins();
            fats += ingredient.getFats();
            carbs += ingredient.getCarbs();
        }

        meal.setTotalCalories(calories);
        meal.setTotalProteins(proteins);
        meal.setTotalFats(fats);
        meal.setTotalCarbs(carbs);
        mealRepository.updateTotals(meal);
    }

    @Override
    public MealIngredientResponse addIngredient(MealIngredientRequest request) {
        validateRequest(request);

        mealRepository.findById(request.mealId())
                .orElseThrow(() -> new RuntimeException("Meal not found"));

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        NutritionCalculator.Nutrition nutrition = NutritionCalculator.fromProduct(product, request.grams());

        MealIngredient ingredient = new MealIngredient(
                null,
                request.mealId(),
                request.productId(),
                request.grams(),
                nutrition.calories(),
                nutrition.proteins(),
                nutrition.fats(),
                nutrition.carbs()
        );

        MealIngredient saved = mealIngredientRepository.save(ingredient);
        recalculateMealTotals(request.mealId());

        return MealIngredientMapper.toResponse(saved);
    }

    @Override
    public List<MealIngredientResponse> getIngredientsByMeal(Long mealId) {
        Validator.requireValidId(mealId, "meal id");

        mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("Meal not found"));

        return mealIngredientRepository.findByMealId(mealId).stream()
                .map(MealIngredientMapper::toResponse)
                .toList();
    }
}
