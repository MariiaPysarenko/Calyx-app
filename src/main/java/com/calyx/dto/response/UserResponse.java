package com.calyx.dto.response;

public record UserResponse(
        Long id,
        String name,
        String email,
        int age,
        double weight,
        int height,
        String goal) {
}
