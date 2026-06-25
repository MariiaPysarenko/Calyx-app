package com.calyx.mapper;

import com.calyx.dto.response.MealIngredientResponse;
import com.calyx.model.MealIngredient;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MealIngredientMapper {

    public static MealIngredientResponse toResponse(MealIngredient ingredient) {
        return new MealIngredientResponse(
                ingredient.getId(),
                ingredient.getProductId(),
                ingredient.getGrams(),
                ingredient.getCalories(),
                ingredient.getProteins(),
                ingredient.getFats(),
                ingredient.getCarbs()
        );
    }

    public static MealIngredient mapRow(ResultSet rs) throws SQLException {
        return new MealIngredient(
                rs.getLong("id"),
                rs.getLong("meal_id"),
                rs.getLong("product_id"),
                rs.getDouble("grams"),
                rs.getInt("calories"),
                rs.getDouble("proteins"),
                rs.getDouble("fats"),
                rs.getDouble("carbs")
        );
    }
}
