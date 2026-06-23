package com.calyx.repository.impl;

import com.calyx.mapper.UserMapper;
import com.calyx.model.User;
import com.calyx.repository.UserRepository;
import com.calyx.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public User save(User user) {
        String sql = """
                INSERT INTO users (name, email, password, age, weight_kg, height_cm, goal)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                RETURNING id
                """;

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setInt(4, user.getAge());
            stmt.setDouble(5, user.getWeiqht());
            stmt.setInt(6, user.getHeight());
            stmt.setString(7, user.getGoal());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user.setId(rs.getLong("id"));
            }

            return user;

        } catch (SQLException e) {
            throw new RuntimeException("Error saving user", e);
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = """
                SELECT id, name, email, password, age, weight_kg, height_cm, goal
                FROM users
                WHERE id = ?
                """;

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(UserMapper.mapRow(rs));
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by id", e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = """
                SELECT id, name, email, password, age, weight_kg, height_cm, goal
                FROM users
                WHERE email = ?
                """;

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(UserMapper.mapRow(rs));
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by email", e);
        }
    }

    @Override
    public List<User> findAll() {
        String sql = """
                SELECT id, name, email, password, age, weight_kg, height_cm, goal
                FROM users
                ORDER BY id ASC
                """;

        List<User> users = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(UserMapper.mapRow(rs));
            }

            return users;

        } catch (SQLException e) {
            throw new RuntimeException("Error finding all users", e);
        }
    }
}
