package com.calyx.controller;

import com.calyx.dto.request.MealRequest;
import com.calyx.dto.request.DailyLogProductRequest;
import com.calyx.dto.response.MealResponse;
import com.calyx.dto.response.DailyLogEntryResponse;
import com.calyx.service.DailyLogService;
import com.calyx.service.MealIngredientService;
import com.calyx.service.MealService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MealControllerTest {

    @Mock
    private MealService mealService;

    @Mock
    private MealIngredientService mealIngredientService;

    @Mock
    private DailyLogService dailyLogService;

    private MealController mealController;

    @Before
    public void setUp() {
        mealController = new MealController(mealService, mealIngredientService, dailyLogService);
    }

    @Test
    public void createMeal_delegatesToService() {
        MealRequest request = new MealRequest(1L, "Soup");
        MealResponse expected = new MealResponse(1L, 1L, "Soup", 0, 0, 0, 0);
        when(mealService.createMeal(request)).thenReturn(expected);

        MealResponse response = mealController.createMeal(request);

        assertEquals("Soup", response.name());
        verify(mealService).createMeal(request);
    }

    @Test
    public void logProduct_delegatesToDailyLogService() {
        DailyLogProductRequest request = new DailyLogProductRequest(
                1L, 2L, 100.0, LocalDate.of(2026, 6, 25)
        );
        DailyLogEntryResponse expected = new DailyLogEntryResponse(
                1L, request.logDate(), "PRODUCT", "Apple", 100.0, null, 52, 0.3, 0.2, 13.8
        );
        when(dailyLogService.logProduct(request)).thenReturn(expected);

        DailyLogEntryResponse response = mealController.logProduct(request);

        assertEquals("Apple", response.displayName());
        verify(dailyLogService).logProduct(request);
    }
}
