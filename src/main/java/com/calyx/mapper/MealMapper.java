package com.calyx.mapper;

import com.calyx.dto.request.MealRequest;
import com.calyx.dto.response.MealResponse;
import com.calyx.model.Meal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class MealMapper {

    public static Meal toEntity(MealRequest request) {
        return new Meal(
                null,
                request.userId(),
                request.mealType(),
                request.dateTime()
        );
    }

    public static MealResponse toResponse(Meal meal) {
        return new MealResponse(
                meal.getId(),
                meal.getUserId(),
                meal.getMealType(),
                meal.getDateTime()
        );
    }

    public static Meal mapRow(ResultSet rs) throws SQLException {
        Timestamp timestamp = rs.getTimestamp("date_time");
        return new Meal(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getString("meal_type"),
                timestamp.toLocalDateTime()
        );
    }
}
