package com.calyx.controller;

import com.calyx.dto.request.MealEntryRequest;
import com.calyx.dto.request.MealRequest;
import com.calyx.dto.response.MealEntryResponse;
import com.calyx.dto.response.MealResponse;
import com.calyx.service.MealEntryService;
import com.calyx.service.MealService;

import java.util.List;

public class MealController {

    private final MealService mealService;
    private final MealEntryService mealEntryService;

    public MealController(MealService mealService, MealEntryService mealEntryService) {
        this.mealService = mealService;
        this.mealEntryService = mealEntryService;
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

    public MealEntryResponse addEntry(MealEntryRequest request) {
        return mealEntryService.addEntry(request);
    }

    public List<MealEntryResponse> getEntriesByMeal(Long mealId) {
        return mealEntryService.getEntriesByMeal(mealId);
    }

    public void deleteEntry(Long id) {
        mealEntryService.deleteEntry(id);
    }
}
