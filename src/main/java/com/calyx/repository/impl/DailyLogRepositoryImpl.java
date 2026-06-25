package com.calyx.repository.impl;

import com.calyx.mapper.DailyLogMapper;
import com.calyx.model.DailyLogEntry;
import com.calyx.repository.DailyLogRepository;
import com.calyx.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DailyLogRepositoryImpl implements DailyLogRepository {

    @Override
    public DailyLogEntry save(DailyLogEntry entry) {
        String sql = """
                INSERT INTO daily_log (user_id, log_date, entry_type, product_id, meal_id,
                                       grams, servings, calories, proteins, fats, carbs)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                RETURNING id
                """;

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, entry.getUserId());
            stmt.setDate(2, Date.valueOf(entry.getLogDate()));
            stmt.setString(3, entry.getEntryType());

            if (entry.getProductId() != null) {
                stmt.setLong(4, entry.getProductId());
            } else {
                stmt.setNull(4, Types.BIGINT);
            }

            if (entry.getMealId() != null) {
                stmt.setLong(5, entry.getMealId());
            } else {
                stmt.setNull(5, Types.BIGINT);
            }

            if (entry.getGrams() != null) {
                stmt.setDouble(6, entry.getGrams());
            } else {
                stmt.setNull(6, Types.DOUBLE);
            }

            if (entry.getServings() != null) {
                stmt.setDouble(7, entry.getServings());
            } else {
                stmt.setNull(7, Types.DOUBLE);
            }

            stmt.setInt(8, entry.getCalories());
            stmt.setDouble(9, entry.getProteins());
            stmt.setDouble(10, entry.getFats());
            stmt.setDouble(11, entry.getCarbs());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                entry.setId(rs.getLong("id"));
            }
            return entry;

        } catch (SQLException e) {
            throw new RuntimeException("Error saving daily log entry", e);
        }
    }

    @Override
    public List<DailyLogEntry> findByUserIdAndDate(Long userId, LocalDate date) {
        String sql = """
                SELECT id, user_id, log_date, entry_type, product_id, meal_id,
                       grams, servings, calories, proteins, fats, carbs
                FROM daily_log
                WHERE user_id = ? AND log_date = ?
                ORDER BY id ASC
                """;

        List<DailyLogEntry> entries = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            stmt.setDate(2, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                entries.add(DailyLogMapper.mapRow(rs));
            }
            return entries;

        } catch (SQLException e) {
            throw new RuntimeException("Error finding daily log entries", e);
        }
    }

    @Override
    public int sumCaloriesByUserIdAndDate(Long userId, LocalDate date) {
        String sql = """
                SELECT COALESCE(SUM(calories), 0)
                FROM daily_log
                WHERE user_id = ? AND log_date = ?
                """;

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            stmt.setDate(2, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error summing daily log calories", e);
        }
    }
}
