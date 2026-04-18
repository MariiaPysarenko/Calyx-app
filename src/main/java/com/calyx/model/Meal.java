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
public class Meal {
    private Long id;
    private Long userId;
    private String mealType;
    private LocalDateTime dateTime;
}
