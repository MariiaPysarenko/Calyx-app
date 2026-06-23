package com.calyx.dto.request;

public record CreateUserRequest(
        String name,
        String email,
        String password,
        int age,
        double weight,
        int height,
        String goal) {
}
