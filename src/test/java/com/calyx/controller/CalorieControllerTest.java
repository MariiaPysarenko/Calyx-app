package com.calyx.controller;

import com.calyx.dto.response.DailyCaloriesResponse;
import com.calyx.service.CalorieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CalorieControllerTest {

    @Mock
    private CalorieService calorieService;

    private CalorieController calorieController;

    @Before
    public void setUp() {
        calorieController = new CalorieController(calorieService);
    }

    @Test
    public void getDailyCalories_delegatesToService() {
        LocalDate date = LocalDate.of(2026, 6, 25);
        when(calorieService.getDailyCalories(1L, date))
                .thenReturn(new DailyCaloriesResponse(1L, date, 750));

        DailyCaloriesResponse response = calorieController.getDailyCalories(1L, date);

        assertEquals(750, response.totalCalories());
    }
}
