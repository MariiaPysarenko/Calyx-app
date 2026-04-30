package com.calyx.repository;

import com.calyx.model.MealEntry;

import java.util.List;

public interface MealEntryRepository {

    MealEntry save(MealEntry entry);

    List<MealEntry> findByMealId(Long mealId);

    void deleteById(Long id);
}