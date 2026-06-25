package com.calyx.servlet;

import com.calyx.controller.CalorieController;
import com.calyx.controller.MealController;
import com.calyx.controller.ProductController;
import com.calyx.repository.impl.BmiRepositoryImpl;
import com.calyx.repository.impl.DailyLogRepositoryImpl;
import com.calyx.repository.impl.MealIngredientRepositoryImpl;
import com.calyx.repository.impl.MealRepositoryImpl;
import com.calyx.repository.impl.ProductRepositoryImpl;
import com.calyx.repository.impl.UserRepositoryImpl;
import com.calyx.service.impl.BmiServiceImpl;
import com.calyx.service.impl.CalorieServiceImpl;
import com.calyx.service.impl.DailyLogServiceImpl;
import com.calyx.service.impl.MealIngredientServiceImpl;
import com.calyx.service.impl.MealServiceImpl;
import com.calyx.service.impl.ProductServiceImpl;
import com.calyx.service.impl.UserServiceImpl;

public final class WebAppFactory {

    private static final UserRepositoryImpl USER_REPOSITORY = new UserRepositoryImpl();
    private static final ProductRepositoryImpl PRODUCT_REPOSITORY = new ProductRepositoryImpl();
    private static final MealRepositoryImpl MEAL_REPOSITORY = new MealRepositoryImpl();
    private static final MealIngredientRepositoryImpl MEAL_INGREDIENT_REPOSITORY = new MealIngredientRepositoryImpl();
    private static final DailyLogRepositoryImpl DAILY_LOG_REPOSITORY = new DailyLogRepositoryImpl();
    private static final BmiRepositoryImpl BMI_REPOSITORY = new BmiRepositoryImpl();

    private static final ProductController PRODUCT_CONTROLLER =
            new ProductController(new ProductServiceImpl(PRODUCT_REPOSITORY));
    private static final MealController MEAL_CONTROLLER = new MealController(
            new MealServiceImpl(MEAL_REPOSITORY, USER_REPOSITORY),
            new MealIngredientServiceImpl(MEAL_INGREDIENT_REPOSITORY, MEAL_REPOSITORY, PRODUCT_REPOSITORY),
            new DailyLogServiceImpl(DAILY_LOG_REPOSITORY, PRODUCT_REPOSITORY, MEAL_REPOSITORY, USER_REPOSITORY)
    );
    private static final CalorieController CALORIE_CONTROLLER = new CalorieController(
            new CalorieServiceImpl(DAILY_LOG_REPOSITORY, USER_REPOSITORY)
    );

    private WebAppFactory() {
    }

    public static ProductController productController() {
        return PRODUCT_CONTROLLER;
    }

    public static MealController mealController() {
        return MEAL_CONTROLLER;
    }

    public static CalorieController calorieController() {
        return CALORIE_CONTROLLER;
    }

    public static UserRepositoryImpl userRepository() {
        return USER_REPOSITORY;
    }

    public static BmiRepositoryImpl bmiRepository() {
        return BMI_REPOSITORY;
    }

    public static long defaultUserId() {
        return userRepository().findByEmail("maria@calyx.local")
                .or(() -> userRepository().findById(1L))
                .map(user -> user.getId())
                .orElse(1L);
    }

    public static String defaultUserName() {
        return userRepository().findByEmail("maria@calyx.local")
                .or(() -> userRepository().findById(1L))
                .map(user -> user.getName())
                .orElse("Maria");
    }
}
