package com.calyx.repository.impl;

import com.calyx.mapper.MealEntryMapper;
import com.calyx.model.MealEntry;
import com.calyx.repository.MealEntryRepository;
import com.calyx.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MealEntryRepositoryImpl implements MealEntryRepository {

    @Override
    public MealEntry save(MealEntry entry) {
        String sql = """
                INSERT INTO meal_entry (meal_id, product_id, grams, calories)
                VALUES (?, ?, ?, ?)
                RETURNING id
                """;

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, entry.getMealId());
            stmt.setLong(2, entry.getProductId());
            stmt.setDouble(3, entry.getGrams());
            stmt.setInt(4, entry.getCalories());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                entry.setId(rs.getLong("id"));
            }

            return entry;

        } catch (SQLException e) {
            throw new RuntimeException("Error saving meal entry", e);
        }
    }

    @Override
    public List<MealEntry> findByMealId(Long mealId) {
        String sql = """
                SELECT id, meal_id, product_id, grams, calories
                FROM meal_entry
                WHERE meal_id = ?
                ORDER BY id ASC
                """;

        List<MealEntry> entries = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, mealId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                entries.add(MealEntryMapper.mapRow(rs));
            }

            return entries;

        } catch (SQLException e) {
            throw new RuntimeException("Error finding meal entries by meal id", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = """
                DELETE FROM meal_entry
                WHERE id = ?
                """;

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting meal entry", e);
        }
    }
}