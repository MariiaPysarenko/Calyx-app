package com.calyx.service;

import com.calyx.dto.response.DailyCaloriesResponse;

import java.time.LocalDate;

public interface CalorieService {

    DailyCaloriesResponse getDailyCalories(Long userId, LocalDate date);
}
