package com.calyx.repository.impl;

import com.calyx.mapper.BmiMapper;
import com.calyx.model.Bmi;
import com.calyx.repository.BmiRepository;
import com.calyx.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BmiRepositoryImpl implements BmiRepository{
    @Override
    public Bmi save(Bmi bmi) {
        String sql = """
        INSERT INTO bmi (user_id, weight_kg, height_cm, bmi_value, category, calculated_at)
        VALUES (?, ?, ?, ?, ?, ?)
        RETURNING id
        """;

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, bmi.getUserId());
            stmt.setDouble(2, bmi.getWeightKg());
            stmt.setInt(3, bmi.getHeightCm());
            stmt.setDouble(4, bmi.getBmiValue());
            stmt.setString(5, bmi.getCategory());
            stmt.setTimestamp(6, Timestamp.valueOf(bmi.getCalculatedAt()));

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                bmi.setId(rs.getLong("id"));
            }

            return bmi;

        } catch (SQLException e) {
            throw new RuntimeException("Error saving BMI", e);
        }
    }

    @Override
    public List<Bmi> findByUserId(Long userId) {
        String sql = "SELECT * FROM bmi WHERE user_id = ?";

        List<Bmi> list = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(BmiMapper.mapRow(rs));
            }

            return list;

        } catch (SQLException e) {
            throw new RuntimeException("Error finding BMI by userId", e);
        }
    }

    @Override
    public List<Bmi> findAll() {
        String sql = "SELECT * FROM bmi";

        List<Bmi> list = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(BmiMapper.mapRow(rs));
            }

            return list;

        } catch (SQLException e) {
            throw new RuntimeException("Error finding all BMI", e);
        }
    }
}
