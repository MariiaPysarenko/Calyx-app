package com.calyx.repository;

import com.calyx.model.DailyLogEntry;

import java.time.LocalDate;
import java.util.List;

public interface DailyLogRepository {

    DailyLogEntry save(DailyLogEntry entry);

    List<DailyLogEntry> findByUserIdAndDate(Long userId, LocalDate date);

    int sumCaloriesByUserIdAndDate(Long userId, LocalDate date);
}
