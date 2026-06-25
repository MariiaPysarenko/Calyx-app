package com.calyx.service.impl;

import com.calyx.dto.response.DailyCaloriesResponse;
import com.calyx.repository.DailyLogRepository;
import com.calyx.repository.UserRepository;
import com.calyx.service.CalorieService;
import com.calyx.util.Validator;

import java.time.LocalDate;

public class CalorieServiceImpl implements CalorieService {

    private final DailyLogRepository dailyLogRepository;
    private final UserRepository userRepository;

    public CalorieServiceImpl(DailyLogRepository dailyLogRepository, UserRepository userRepository) {
        this.dailyLogRepository = dailyLogRepository;
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

        int totalCalories = dailyLogRepository.sumCaloriesByUserIdAndDate(userId, date);
        return new DailyCaloriesResponse(userId, date, totalCalories);
    }
}
