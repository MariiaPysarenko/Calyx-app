package com.calyx.mapper;

import com.calyx.dto.request.ProductRequest;
import com.calyx.dto.response.ProductResponse;
import com.calyx.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper {

    public static Product toEntity(ProductRequest request) {
        String category = request.category() == null || request.category().isBlank()
                ? "other"
                : request.category().trim().toLowerCase();

        return new Product(
                null,
                request.name(),
                category,
                request.caloriesPer100g(),
                request.proteinsPer100g(),
                request.fatsPer100g(),
                request.carbsPer100g()
        );
    }

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getCaloriesPer100g(),
                product.getProteinsPer100g(),
                product.getFatsPer100g(),
                product.getCarbsPer100g()
        );
    }

    public static Product mapRow(ResultSet rs) throws SQLException {
        return new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("category"),
                rs.getInt("calories_per_100g"),
                rs.getDouble("proteins_per_100g"),
                rs.getDouble("fats_per_100g"),
                rs.getDouble("carbs_per_100g")
        );
    }
}
