package com.calyx.util;

import com.calyx.exception.ValidationException;
import org.junit.Test;

public class ValidatorTest {

    @Test(expected = ValidationException.class)
    public void requireNonBlank_rejectsNull() {
        Validator.requireNonBlank(null, "Name");
    }

    @Test(expected = ValidationException.class)
    public void requireNonBlank_rejectsBlank() {
        Validator.requireNonBlank("   ", "Name");
    }

    @Test
    public void requireNonBlank_acceptsValue() {
        Validator.requireNonBlank("Maria", "Name");
    }

    @Test(expected = ValidationException.class)
    public void requirePositive_rejectsZeroDouble() {
        Validator.requirePositive(0, "Grams");
    }

    @Test(expected = ValidationException.class)
    public void requireValidId_rejectsNull() {
        Validator.requireValidId(null, "user id");
    }

    @Test(expected = ValidationException.class)
    public void requireValidEmail_rejectsInvalidFormat() {
        Validator.requireValidEmail("not-an-email");
    }

    @Test
    public void requireValidEmail_acceptsValidFormat() {
        Validator.requireValidEmail("user@example.com");
    }
}
