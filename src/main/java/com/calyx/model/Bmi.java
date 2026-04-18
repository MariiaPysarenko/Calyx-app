package com.calyx.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Bmi {
    private Long id;
    private Long userId;
    private double weightKg;
    private int heightCm;
    private double bmiValue;
    private String category;
    private LocalDateTime calculatedAt;
}
