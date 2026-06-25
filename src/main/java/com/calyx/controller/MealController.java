package com.calyx.controller;

import com.calyx.dto.request.DailyLogMealRequest;
import com.calyx.dto.request.DailyLogProductRequest;
import com.calyx.dto.request.MealIngredientRequest;
import com.calyx.dto.request.MealRequest;
import com.calyx.dto.response.DailyLogEntryResponse;
import com.calyx.dto.response.MealIngredientResponse;
import com.calyx.dto.response.MealResponse;
import com.calyx.service.DailyLogService;
import com.calyx.service.MealIngredientService;
import com.calyx.service.MealService;

import java.time.LocalDate;
import java.util.List;

public class MealController {

    private final MealService mealService;
    private final MealIngredientService mealIngredientService;
    private final DailyLogService dailyLogService;

    public MealController(MealService mealService,
                          MealIngredientService mealIngredientService,
                          DailyLogService dailyLogService) {
        this.mealService = mealService;
        this.mealIngredientService = mealIngredientService;
        this.dailyLogService = dailyLogService;
    }

    public MealResponse createMeal(MealRequest request) {
        return mealService.createMeal(request);
    }

    public MealResponse getMealById(Long id) {
        return mealService.getMealById(id);
    }

    public List<MealResponse> getMealsByUser(Long userId) {
        return mealService.getMealsByUser(userId);
    }

    public MealIngredientResponse addIngredient(MealIngredientRequest request) {
        return mealIngredientService.addIngredient(request);
    }

    public List<MealIngredientResponse> getIngredientsByMeal(Long mealId) {
        return mealIngredientService.getIngredientsByMeal(mealId);
    }

    public DailyLogEntryResponse logProduct(DailyLogProductRequest request) {
        return dailyLogService.logProduct(request);
    }

    public DailyLogEntryResponse logMeal(DailyLogMealRequest request) {
        return dailyLogService.logMeal(request);
    }

    public List<DailyLogEntryResponse> getDailyLog(Long userId, LocalDate date) {
        return dailyLogService.getLogForDate(userId, date);
    }
}
