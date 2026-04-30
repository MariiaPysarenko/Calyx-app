package com.calyx.service;

import com.calyx.dto.request.MealEntryRequest;
import com.calyx.dto.response.MealEntryResponse;

import java.util.List;

public interface MealEntryService {

    MealEntryResponse addEntry(MealEntryRequest request);

    List<MealEntryResponse> getEntriesByMeal(Long mealId);

    void deleteEntry(Long id);
}