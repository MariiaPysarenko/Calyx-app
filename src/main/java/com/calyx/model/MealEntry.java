package com.calyx.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class MealEntry {
    private Long id;
    private Long mealId;
    private Long productId;
    private double grams;
    private int calories;
}
