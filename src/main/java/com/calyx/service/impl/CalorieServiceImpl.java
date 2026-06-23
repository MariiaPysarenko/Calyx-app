package com.calyx.service.impl;

import com.calyx.dto.response.DailyCaloriesResponse;
import com.calyx.model.Meal;
import com.calyx.model.MealEntry;
import com.calyx.repository.MealEntryRepository;
import com.calyx.repository.MealRepository;
import com.calyx.repository.UserRepository;
import com.calyx.service.CalorieService;
import com.calyx.util.Validator;

import java.time.LocalDate;
import java.util.List;

public class CalorieServiceImpl implements CalorieService {

    private final MealRepository mealRepository;
    private final MealEntryRepository mealEntryRepository;
    private final UserRepository userRepository;

    public CalorieServiceImpl(MealRepository mealRepository,
                              MealEntryRepository mealEntryRepository,
                              UserRepository userRepository) {
        this.mealRepository = mealRepository;
        this.mealEntryRepository = mealEntryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public DailyCaloriesResponse getDailyCalories(Long userId, LocalDate date) {
        Validator.requireValidId(userId, "user id");
        if (date == null) {
            throw new IllegalArgumentException("Date must not be null");
        }

        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Meal> meals = mealRepository.findByUserId(userId);
        int totalCalories = 0;

        for (Meal meal : meals) {
            if (!meal.getDateTime().toLocalDate().equals(date)) {
                continue;
            }

            List<MealEntry> entries = mealEntryRepository.findByMealId(meal.getId());
            for (MealEntry entry : entries) {
                totalCalories += entry.getCalories();
            }
        }

        return new DailyCaloriesResponse(userId, date, totalCalories);
    }
}
