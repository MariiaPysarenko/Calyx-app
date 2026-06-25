package com.calyx.mapper;

import com.calyx.dto.response.DailyLogEntryResponse;
import com.calyx.model.DailyLogEntry;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DailyLogMapper {

    public static DailyLogEntryResponse toResponse(DailyLogEntry entry, String displayName) {
        return new DailyLogEntryResponse(
                entry.getId(),
                entry.getLogDate(),
                entry.getEntryType(),
                displayName,
                entry.getGrams(),
                entry.getServings(),
                entry.getCalories(),
                entry.getProteins(),
                entry.getFats(),
                entry.getCarbs()
        );
    }

    public static DailyLogEntry mapRow(ResultSet rs) throws SQLException {
        Long productId = rs.getObject("product_id") != null ? rs.getLong("product_id") : null;
        Long mealId = rs.getObject("meal_id") != null ? rs.getLong("meal_id") : null;
        Double grams = rs.getObject("grams") != null ? rs.getDouble("grams") : null;
        Double servings = rs.getObject("servings") != null ? rs.getDouble("servings") : null;

        return new DailyLogEntry(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getDate("log_date").toLocalDate(),
                rs.getString("entry_type"),
                productId,
                mealId,
                grams,
                servings,
                rs.getInt("calories"),
                rs.getDouble("proteins"),
                rs.getDouble("fats"),
                rs.getDouble("carbs")
        );
    }
}
