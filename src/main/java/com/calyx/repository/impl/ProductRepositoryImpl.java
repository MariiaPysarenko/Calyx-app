package com.calyx.repository.impl;

import com.calyx.mapper.ProductMapper;
import com.calyx.model.Product;
import com.calyx.repository.ProductRepository;
import com.calyx.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public Product save(Product product) {
        String sql = """
        INSERT INTO product (name, category, calories_per_100g, proteins_per_100g, fats_per_100g, carbs_per_100g)
        VALUES (?, ?, ?, ?, ?, ?)
        RETURNING id
        """;

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getCategory());
            stmt.setInt(3, product.getCaloriesPer100g());
            stmt.setDouble(4, product.getProteinsPer100g());
            stmt.setDouble(5, product.getFatsPer100g());
            stmt.setDouble(6, product.getCarbsPer100g());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                product.setId(rs.getLong("id"));
            }

            return product;

        } catch (SQLException e) {
            throw new RuntimeException("Error saving product", e);
        }
    }

    @Override
    public Optional<Product> findById(Long id) {
        String sql = """
        SELECT id, name, category, calories_per_100g, proteins_per_100g, fats_per_100g, carbs_per_100g
        FROM product
        WHERE id = ?
        """;

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(ProductMapper.mapRow(rs));
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Error finding product by id", e);
        }
    }

    @Override
    public List<Product> findAll() {
        String sql = """
        SELECT id, name, category, calories_per_100g, proteins_per_100g, fats_per_100g, carbs_per_100g
        FROM product
        ORDER BY name ASC
        """;

        List<Product> products = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                products.add(ProductMapper.mapRow(rs));
            }

            return products;

        } catch (SQLException e) {
            throw new RuntimeException("Error finding all products", e);
        }
    }

    @Override
    public Product update(Product product) {
        String sql = """
        UPDATE product
        SET name = ?, category = ?, calories_per_100g = ?, proteins_per_100g = ?, fats_per_100g = ?, carbs_per_100g = ?
        WHERE id = ?
        """;

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getCategory());
            stmt.setInt(3, product.getCaloriesPer100g());
            stmt.setDouble(4, product.getProteinsPer100g());
            stmt.setDouble(5, product.getFatsPer100g());
            stmt.setDouble(6, product.getCarbsPer100g());
            stmt.setLong(7, product.getId());

            stmt.executeUpdate();

            return product;

        } catch (SQLException e) {
            throw new RuntimeException("Error updating product", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = """
        DELETE FROM product
        WHERE id = ?
        """;

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting product", e);
        }
    }
}
