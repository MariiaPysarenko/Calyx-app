package com.calyx.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = DatabaseConfig.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("application.properties not found");
            }
            PROPERTIES.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load application.properties", e);
        }
    }

    public static String getUrl() {
        return PROPERTIES.getProperty("db.url");
    }

    public static String getUser() {
        return PROPERTIES.getProperty("db.user");
    }

    public static String getPassword() {
        return PROPERTIES.getProperty("db.password");
    }
}
