package com.calyx.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Meal {
    private Long id;
    private Long userId;
    private String name;
    private int totalCalories;
    private double totalProteins;
    private double totalFats;
    private double totalCarbs;
}
