package com.calyx.repository.impl;

import com.calyx.mapper.MealMapper;
import com.calyx.model.Meal;
import com.calyx.repository.MealRepository;
import com.calyx.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MealRepositoryImpl implements MealRepository {

    @Override
    public Meal save(Meal meal) {
        String sql = """
                INSERT INTO meal (user_id, name, total_calories, total_proteins, total_fats, total_carbs)
                VALUES (?, ?, ?, ?, ?, ?)
                RETURNING id
                """;

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, meal.getUserId());
            stmt.setString(2, meal.getName());
            stmt.setInt(3, meal.getTotalCalories());
            stmt.setDouble(4, meal.getTotalProteins());
            stmt.setDouble(5, meal.getTotalFats());
            stmt.setDouble(6, meal.getTotalCarbs());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                meal.setId(rs.getLong("id"));
            }
            return meal;

        } catch (SQLException e) {
            throw new RuntimeException("Error saving meal", e);
        }
    }

    @Override
    public Optional<Meal> findById(Long id) {
        String sql = """
                SELECT id, user_id, name, total_calories, total_proteins, total_fats, total_carbs
                FROM meal
                WHERE id = ?
                """;

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(MealMapper.mapRow(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Error finding meal by id", e);
        }
    }

    @Override
    public List<Meal> findByUserId(Long userId) {
        String sql = """
                SELECT id, user_id, name, total_calories, total_proteins, total_fats, total_carbs
                FROM meal
                WHERE user_id = ?
                ORDER BY name ASC
                """;

        List<Meal> meals = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                meals.add(MealMapper.mapRow(rs));
            }
            return meals;

        } catch (SQLException e) {
            throw new RuntimeException("Error finding meals by user id", e);
        }
    }

    @Override
    public void updateTotals(Meal meal) {
        String sql = """
                UPDATE meal
                SET total_calories = ?, total_proteins = ?, total_fats = ?, total_carbs = ?
                WHERE id = ?
                """;

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, meal.getTotalCalories());
            stmt.setDouble(2, meal.getTotalProteins());
            stmt.setDouble(3, meal.getTotalFats());
            stmt.setDouble(4, meal.getTotalCarbs());
            stmt.setLong(5, meal.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error updating meal totals", e);
        }
    }
}
