package com.calyx.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class DailyLogEntry {
    private Long id;
    private Long userId;
    private LocalDate logDate;
    private String entryType;
    private Long productId;
    private Long mealId;
    private Double grams;
    private Double servings;
    private int calories;
    private double proteins;
    private double fats;
    private double carbs;
}
