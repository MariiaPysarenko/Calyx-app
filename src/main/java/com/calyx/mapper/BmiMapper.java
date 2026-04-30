package com.calyx.mapper;

import com.calyx.dto.request.BmiRequest;
import com.calyx.dto.response.BmiResponse;
import com.calyx.model.Bmi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class BmiMapper {

    public static Bmi toEntity(BmiRequest request, double bmiValue, String category) {
        return new Bmi(
                null,
                request.userId(),
                request.weightKg(),
                request.heightCm(),
                bmiValue,
                category,
                LocalDateTime.now()
        );
    }

    public static BmiResponse toResponse(Bmi bmi) {
        return new BmiResponse(
                bmi.getBmiValue(),
                bmi.getCategory()
        );
    }

    public static Bmi mapRow(ResultSet rs) throws SQLException {
        return new Bmi(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getDouble("weight_kg"),
                rs.getInt("height_cm"),
                rs.getDouble("bmi_value"),
                rs.getString("category"),
                rs.getTimestamp("calculated_at").toLocalDateTime()
        );
    }
}