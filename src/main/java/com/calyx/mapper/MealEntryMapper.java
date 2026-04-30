package com.calyx.mapper;

import com.calyx.dto.response.MealEntryResponse;
import com.calyx.model.MealEntry;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MealEntryMapper {

    public static MealEntryResponse toResponse(MealEntry entry) {
        return new MealEntryResponse(
                entry.getId(),
                entry.getProductId(),
                entry.getGrams(),
                entry.getCalories()
        );
    }

    public static MealEntry mapRow(ResultSet rs) throws SQLException {
        return new MealEntry(
                rs.getLong("id"),
                rs.getLong("meal_id"),
                rs.getLong("product_id"),
                rs.getDouble("grams"),
                rs.getInt("calories")
        );
    }
}