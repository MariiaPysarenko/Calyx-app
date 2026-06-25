package com.calyx.service.impl;

import com.calyx.dto.request.MealIngredientRequest;
import com.calyx.dto.response.MealIngredientResponse;
import com.calyx.model.Meal;
import com.calyx.model.MealIngredient;
import com.calyx.repository.MealIngredientRepository;
import com.calyx.repository.MealRepository;
import com.calyx.repository.ProductRepository;
import com.calyx.testutil.TestData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MealIngredientServiceImplTest {

    @Mock
    private MealIngredientRepository mealIngredientRepository;

    @Mock
    private MealRepository mealRepository;

    @Mock
    private ProductRepository productRepository;

    private MealIngredientServiceImpl mealIngredientService;

    @Before
    public void setUp() {
        mealIngredientService = new MealIngredientServiceImpl(
                mealIngredientRepository, mealRepository, productRepository
        );
    }

    @Test
    public void addIngredient_recalculatesMealTotals() {
        when(mealRepository.findById(1L)).thenReturn(Optional.of(TestData.sampleMeal()));
        when(productRepository.findById(1L)).thenReturn(Optional.of(TestData.sampleProduct()));
        when(mealIngredientRepository.save(any(MealIngredient.class))).thenAnswer(invocation -> {
            MealIngredient ingredient = invocation.getArgument(0);
            ingredient.setId(1L);
            return ingredient;
        });
        when(mealIngredientRepository.findByMealId(1L)).thenReturn(List.of(TestData.sampleIngredient()));

        MealIngredientResponse response = mealIngredientService.addIngredient(
                new MealIngredientRequest(1L, 1L, 150.0)
        );

        assertEquals(248, response.calories());

        ArgumentCaptor<Meal> mealCaptor = ArgumentCaptor.forClass(Meal.class);
        verify(mealRepository).updateTotals(mealCaptor.capture());
        assertEquals(248, mealCaptor.getValue().getTotalCalories());
    }

    @Test
    public void getIngredientsByMeal_returnsMappedList() {
        when(mealRepository.findById(1L)).thenReturn(Optional.of(TestData.sampleMeal()));
        when(mealIngredientRepository.findByMealId(1L)).thenReturn(List.of(TestData.sampleIngredient()));

        List<MealIngredientResponse> ingredients = mealIngredientService.getIngredientsByMeal(1L);

        assertEquals(1, ingredients.size());
        assertEquals(150.0, ingredients.get(0).grams(), 0.001);
    }
}
