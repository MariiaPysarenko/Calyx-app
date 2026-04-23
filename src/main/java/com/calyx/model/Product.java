package com.calyx.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Product {
    private Long id;
    private String name;
    private int caloriesPer100g;
    private double proteinsPer100g;
    private double fatsPer100g;
    private double carbsPer100g;
}
