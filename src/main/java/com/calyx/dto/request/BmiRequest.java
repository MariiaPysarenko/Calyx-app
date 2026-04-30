package com.calyx.dto.request;

public record BmiRequest(Long userId,
                         double weightKg,
                         int heightCm) {}
