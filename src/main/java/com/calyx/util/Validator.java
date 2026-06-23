package com.calyx.util;

import com.calyx.exception.ValidationException;

public class Validator {

    public static void requireNonBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new ValidationException(fieldName + " must not be empty");
        }
    }

    public static void requirePositive(double value, String fieldName) {
        if (value <= 0) {
            throw new ValidationException(fieldName + " must be positive");
        }
    }

    public static void requirePositive(int value, String fieldName) {
        if (value <= 0) {
            throw new ValidationException(fieldName + " must be positive");
        }
    }

    public static void requireNonNegative(int value, String fieldName) {
        if (value < 0) {
            throw new ValidationException(fieldName + " must be >= 0");
        }
    }

    public static void requireValidId(Long id, String fieldName) {
        if (id == null || id <= 0) {
            throw new ValidationException("Invalid " + fieldName);
        }
    }

    public static void requireValidEmail(String email) {
        requireNonBlank(email, "Email");
        if (!email.contains("@") || !email.contains(".")) {
            throw new ValidationException("Invalid email format");
        }
    }
}
