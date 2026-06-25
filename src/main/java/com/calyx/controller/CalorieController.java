package com.calyx.controller;

import com.calyx.dto.response.DailyCaloriesResponse;
import com.calyx.service.CalorieService;

import java.time.LocalDate;

public class CalorieController {

    private final CalorieService calorieService;

    public CalorieController(CalorieService calorieService) {
        this.calorieService = calorieService;
    }

    public DailyCaloriesResponse getDailyCalories(Long userId, LocalDate date) {
        return calorieService.getDailyCalories(userId, date);
    }
}
