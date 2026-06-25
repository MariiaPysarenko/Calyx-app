package com.calyx.repository.impl;

import com.calyx.mapper.MealIngredientMapper;
import com.calyx.model.MealIngredient;
import com.calyx.repository.MealIngredientRepository;
import com.calyx.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MealIngredientRepositoryImpl implements MealIngredientRepository {

    @Override
    public MealIngredient save(MealIngredient ingredient) {
        String sql = """
                INSERT INTO meal_ingredient (meal_id, product_id, grams, calories, proteins, fats, carbs)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                RETURNING id
                """;

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, ingredient.getMealId());
            stmt.setLong(2, ingredient.getProductId());
            stmt.setDouble(3, ingredient.getGrams());
            stmt.setInt(4, ingredient.getCalories());
            stmt.setDouble(5, ingredient.getProteins());
            stmt.setDouble(6, ingredient.getFats());
            stmt.setDouble(7, ingredient.getCarbs());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ingredient.setId(rs.getLong("id"));
            }
            return ingredient;

        } catch (SQLException e) {
            throw new RuntimeException("Error saving meal ingredient", e);
        }
    }

    @Override
    public List<MealIngredient> findByMealId(Long mealId) {
        String sql = """
                SELECT id, meal_id, product_id, grams, calories, proteins, fats, carbs
                FROM meal_ingredient
                WHERE meal_id = ?
                ORDER BY id ASC
                """;

        List<MealIngredient> ingredients = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, mealId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ingredients.add(MealIngredientMapper.mapRow(rs));
            }
            return ingredients;

        } catch (SQLException e) {
            throw new RuntimeException("Error finding meal ingredients", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM meal_ingredient WHERE id = ?";

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting meal ingredient", e);
        }
    }
}
