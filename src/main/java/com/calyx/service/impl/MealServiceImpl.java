package com.calyx.service.impl;

import com.calyx.dto.request.MealRequest;
import com.calyx.dto.response.MealResponse;
import com.calyx.mapper.MealMapper;
import com.calyx.model.Meal;
import com.calyx.repository.MealRepository;
import com.calyx.repository.UserRepository;
import com.calyx.service.MealService;
import com.calyx.util.Validator;

import java.util.List;

public class MealServiceImpl implements MealService {

    private final MealRepository mealRepository;
    private final UserRepository userRepository;

    public MealServiceImpl(MealRepository mealRepository, UserRepository userRepository) {
        this.mealRepository = mealRepository;
        this.userRepository = userRepository;
    }

    private void validateRequest(MealRequest request) {
        Validator.requireValidId(request.userId(), "user id");
        Validator.requireNonBlank(request.name(), "Meal name");
    }

    @Override
    public MealResponse createMeal(MealRequest request) {
        validateRequest(request);

        userRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Meal meal = MealMapper.toEntity(request);
        Meal saved = mealRepository.save(meal);
        return MealMapper.toResponse(saved);
    }

    @Override
    public MealResponse getMealById(Long id) {
        Validator.requireValidId(id, "meal id");

        Meal meal = mealRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Meal not found"));

        return MealMapper.toResponse(meal);
    }

    @Override
    public List<MealResponse> getMealsByUser(Long userId) {
        Validator.requireValidId(userId, "user id");

        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mealRepository.findByUserId(userId).stream()
                .map(MealMapper::toResponse)
                .toList();
    }
}
