package com.calyx.service.impl;

import com.calyx.dto.response.BmiResponse;
import com.calyx.exception.ValidationException;
import com.calyx.service.BmiService;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BmiServiceImpl implements BmiService {

    private void validateData(double weightKg, int heightCm) {
        if (weightKg <= 0){
            throw new ValidationException("Weight must be positive");
        }
        if (heightCm <= 0){
            throw new ValidationException("Height must be positive");
        }
    }

    @Override
    public BmiResponse calculateBmi(double weightKg, int heightCm) {
        validateData(weightKg,heightCm);
        double heightM = heightCm/100.0;
        double rawBmi = weightKg/(heightM * heightM);
        BigDecimal bmi  = BigDecimal.valueOf(rawBmi);
        bmi = bmi.setScale(2, RoundingMode.HALF_UP);
        double roundedBmi = bmi.doubleValue();

        String category = determineCategory(roundedBmi);

        return new BmiResponse(roundedBmi, category);
    }

    private String determineCategory(double bmi) {
        if (bmi < 18.5){
            return "Underweight";
        }
        else if (bmi < 25){
            return "Normal weight";
        }
        else if (bmi < 30){
            return "Overweight";
        }
        else{
            return "Obesity";
        }
    }
}
