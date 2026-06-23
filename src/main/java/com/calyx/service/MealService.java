package com.calyx.service;

import com.calyx.dto.request.MealRequest;
import com.calyx.dto.response.MealResponse;

import java.util.List;

public interface MealService {

    MealResponse createMeal(MealRequest request);

    MealResponse getMealById(Long id);

    List<MealResponse> getMealsByUser(Long userId);
}
