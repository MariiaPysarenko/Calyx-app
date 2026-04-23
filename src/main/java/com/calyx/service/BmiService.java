package com.calyx.service;

import com.calyx.dto.response.BmiResponse;

public interface BmiService {
    BmiResponse calculateBmi(double weightKg, int heightCm);
}
