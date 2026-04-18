package com.calyx.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class DailyNorm {
    private Long id;
    private Long userId;
    private int dailyCalories;
    private double proteinsNorm;
    private double fatsNorm;
    private double carbsNorm;
}
