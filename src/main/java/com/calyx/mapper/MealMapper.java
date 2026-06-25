package com.calyx.mapper;

import com.calyx.dto.request.MealRequest;
import com.calyx.dto.response.MealResponse;
import com.calyx.model.Meal;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MealMapper {

    public static Meal toEntity(MealRequest request) {
        return new Meal(
                null,
                request.userId(),
                request.name(),
                0,
                0,
                0,
                0
        );
    }

    public static MealResponse toResponse(Meal meal) {
        return new MealResponse(
                meal.getId(),
                meal.getUserId(),
                meal.getName(),
                meal.getTotalCalories(),
                meal.getTotalProteins(),
                meal.getTotalFats(),
                meal.getTotalCarbs()
        );
    }

    public static Meal mapRow(ResultSet rs) throws SQLException {
        return new Meal(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getString("name"),
                rs.getInt("total_calories"),
                rs.getDouble("total_proteins"),
                rs.getDouble("total_fats"),
                rs.getDouble("total_carbs")
        );
    }
}
