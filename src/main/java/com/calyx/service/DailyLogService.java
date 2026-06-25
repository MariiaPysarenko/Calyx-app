package com.calyx.service;

import com.calyx.dto.request.DailyLogMealRequest;
import com.calyx.dto.request.DailyLogProductRequest;
import com.calyx.dto.response.DailyLogEntryResponse;

import java.time.LocalDate;
import java.util.List;

public interface DailyLogService {

    DailyLogEntryResponse logProduct(DailyLogProductRequest request);

    DailyLogEntryResponse logMeal(DailyLogMealRequest request);

    List<DailyLogEntryResponse> getLogForDate(Long userId, LocalDate date);
}
