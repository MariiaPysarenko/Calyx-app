package com.calyx.service.impl;

import com.calyx.dto.request.MealRequest;
import com.calyx.dto.response.MealResponse;
import com.calyx.exception.ValidationException;
import com.calyx.model.Meal;
import com.calyx.repository.MealRepository;
import com.calyx.repository.UserRepository;
import com.calyx.testutil.TestData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MealServiceImplTest {

    @Mock
    private MealRepository mealRepository;

    @Mock
    private UserRepository userRepository;

    private MealServiceImpl mealService;

    @Before
    public void setUp() {
        mealService = new MealServiceImpl(mealRepository, userRepository);
    }

    @Test
    public void createMeal_savesMealForExistingUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(TestData.sampleUser()));
        when(mealRepository.save(any(Meal.class))).thenAnswer(invocation -> {
            Meal meal = invocation.getArgument(0);
            meal.setId(3L);
            return meal;
        });

        MealResponse response = mealService.createMeal(new MealRequest(1L, "Pasta"));

        assertEquals(Long.valueOf(3L), response.id());
        assertEquals("Pasta", response.name());
    }

    @Test
    public void createMeal_rejectsBlankName() {
        assertThrows(ValidationException.class,
                () -> mealService.createMeal(new MealRequest(1L, "  ")));
    }

    @Test
    public void getMealsByUser_returnsUserMeals() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(TestData.sampleUser()));
        when(mealRepository.findByUserId(1L)).thenReturn(List.of(TestData.sampleMeal()));

        List<MealResponse> meals = mealService.getMealsByUser(1L);

        assertEquals(1, meals.size());
        assertEquals("Chicken rice bowl", meals.get(0).name());
    }
}
