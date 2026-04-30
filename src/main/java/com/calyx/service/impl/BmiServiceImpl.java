package com.calyx.service.impl;

import com.calyx.dto.request.BmiRequest;
import com.calyx.dto.response.BmiResponse;
import com.calyx.exception.ValidationException;
import com.calyx.mapper.BmiMapper;
import com.calyx.model.Bmi;
import com.calyx.repository.BmiRepository;
import com.calyx.service.BmiService;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BmiServiceImpl implements BmiService {
    private final BmiRepository bmiRepository;

    public BmiServiceImpl(BmiRepository bmiRepository) {
        this.bmiRepository = bmiRepository;
    }

    private void validateData(double weightKg, int heightCm) {
        if (weightKg <= 0){
            throw new ValidationException("Weight must be positive");
        }
        if (heightCm <= 0){
            throw new ValidationException("Height must be positive");
        }
    }

    @Override
    public BmiResponse calculateAndSave(BmiRequest request) {

        validateData(request.weightKg(), request.heightCm());

        double heightM = request.heightCm() / 100.0;
        double rawBmi = request.weightKg() / (heightM * heightM);

        double rounded = BigDecimal.valueOf(rawBmi)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        String category = determineCategory(rounded);

        Bmi bmi = BmiMapper.toEntity(request, rounded, category);

        Bmi saved = bmiRepository.save(bmi);

        return BmiMapper.toResponse(saved);
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
