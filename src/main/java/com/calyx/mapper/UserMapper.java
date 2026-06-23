package com.calyx.mapper;

import com.calyx.dto.request.CreateUserRequest;
import com.calyx.dto.response.UserResponse;
import com.calyx.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {

    public static User toEntity(CreateUserRequest request, String hashedPassword) {
        return new User(
                null,
                request.name(),
                request.email(),
                hashedPassword,
                request.age(),
                request.weight(),
                request.height(),
                request.goal()
        );
    }

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge(),
                user.getWeiqht(),
                user.getHeight(),
                user.getGoal()
        );
    }

    public static User mapRow(ResultSet rs) throws SQLException {
        return new User(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getInt("age"),
                rs.getDouble("weight_kg"),
                rs.getInt("height_cm"),
                rs.getString("goal")
        );
    }
}
