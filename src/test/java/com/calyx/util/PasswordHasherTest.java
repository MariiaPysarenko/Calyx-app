package com.calyx.util;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class PasswordHasherTest {

    @Test
    public void hashAndVerify_matchingPasswordReturnsTrue() {
        String hashed = PasswordHasher.hash("secret123");

        assertTrue(PasswordHasher.verify("secret123", hashed));
    }

    @Test
    public void verify_wrongPasswordReturnsFalse() {
        String hashed = PasswordHasher.hash("secret123");

        assertFalse(PasswordHasher.verify("wrong", hashed));
    }

    @Test
    public void hash_generatesDifferentSalts() {
        String first = PasswordHasher.hash("same");
        String second = PasswordHasher.hash("same");

        assertNotEquals(first, second);
    }

    @Test
    public void verify_rejectsMalformedStoredValue() {
        assertFalse(PasswordHasher.verify("secret", "not-valid-format"));
    }
}
