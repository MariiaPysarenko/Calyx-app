package com.calyx.repository;

import com.calyx.model.Meal;

import java.util.List;
import java.util.Optional;

public interface MealRepository {

    Meal save(Meal meal);

    Optional<Meal> findById(Long id);

    List<Meal> findByUserId(Long userId);

    void updateTotals(Meal meal);
}
