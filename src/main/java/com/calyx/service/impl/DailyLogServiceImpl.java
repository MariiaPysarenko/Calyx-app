package com.calyx.service.impl;

import com.calyx.dto.request.DailyLogMealRequest;
import com.calyx.dto.request.DailyLogProductRequest;
import com.calyx.dto.response.DailyLogEntryResponse;
import com.calyx.mapper.DailyLogMapper;
import com.calyx.model.DailyLogEntry;
import com.calyx.model.Meal;
import com.calyx.model.Product;
import com.calyx.repository.DailyLogRepository;
import com.calyx.repository.MealRepository;
import com.calyx.repository.ProductRepository;
import com.calyx.repository.UserRepository;
import com.calyx.service.DailyLogService;
import com.calyx.util.NutritionCalculator;
import com.calyx.util.Validator;

import java.time.LocalDate;
import java.util.List;

public class DailyLogServiceImpl implements DailyLogService {

    public static final String ENTRY_TYPE_PRODUCT = "PRODUCT";
    public static final String ENTRY_TYPE_MEAL = "MEAL";

    private final DailyLogRepository dailyLogRepository;
    private final ProductRepository productRepository;
    private final MealRepository mealRepository;
    private final UserRepository userRepository;

    public DailyLogServiceImpl(DailyLogRepository dailyLogRepository,
                               ProductRepository productRepository,
                               MealRepository mealRepository,
                               UserRepository userRepository) {
        this.dailyLogRepository = dailyLogRepository;
        this.productRepository = productRepository;
        this.mealRepository = mealRepository;
        this.userRepository = userRepository;
    }

    private LocalDate resolveDate(LocalDate date) {
        return date != null ? date : LocalDate.now();
    }

    private String resolveProductName(Long productId) {
        return productRepository.findById(productId)
                .map(Product::getName)
                .orElse("Product");
    }

    private String resolveMealName(Long mealId) {
        return mealRepository.findById(mealId)
                .map(Meal::getName)
                .orElse("Meal");
    }

    @Override
    public DailyLogEntryResponse logProduct(DailyLogProductRequest request) {
        Validator.requireValidId(request.userId(), "user id");
        Validator.requireValidId(request.productId(), "product id");
        Validator.requirePositive(request.grams(), "Grams");

        userRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        NutritionCalculator.Nutrition nutrition = NutritionCalculator.fromProduct(product, request.grams());
        LocalDate logDate = resolveDate(request.logDate());

        DailyLogEntry entry = new DailyLogEntry(
                null,
                request.userId(),
                logDate,
                ENTRY_TYPE_PRODUCT,
                request.productId(),
                null,
                request.grams(),
                null,
                nutrition.calories(),
                nutrition.proteins(),
                nutrition.fats(),
                nutrition.carbs()
        );

        DailyLogEntry saved = dailyLogRepository.save(entry);
        return DailyLogMapper.toResponse(saved, product.getName());
    }

    @Override
    public DailyLogEntryResponse logMeal(DailyLogMealRequest request) {
        Validator.requireValidId(request.userId(), "user id");
        Validator.requireValidId(request.mealId(), "meal id");
        Validator.requirePositive(request.servings(), "Servings");

        userRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Meal meal = mealRepository.findById(request.mealId())
                .orElseThrow(() -> new RuntimeException("Meal not found"));

        NutritionCalculator.Nutrition nutrition = NutritionCalculator.fromMealTotals(
                meal.getTotalCalories(),
                meal.getTotalProteins(),
                meal.getTotalFats(),
                meal.getTotalCarbs(),
                request.servings()
        );

        LocalDate logDate = resolveDate(request.logDate());

        DailyLogEntry entry = new DailyLogEntry(
                null,
                request.userId(),
                logDate,
                ENTRY_TYPE_MEAL,
                null,
                request.mealId(),
                null,
                request.servings(),
                nutrition.calories(),
                nutrition.proteins(),
                nutrition.fats(),
                nutrition.carbs()
        );

        DailyLogEntry saved = dailyLogRepository.save(entry);
        return DailyLogMapper.toResponse(saved, meal.getName());
    }

    @Override
    public List<DailyLogEntryResponse> getLogForDate(Long userId, LocalDate date) {
        Validator.requireValidId(userId, "user id");
        LocalDate logDate = resolveDate(date);

        return dailyLogRepository.findByUserIdAndDate(userId, logDate).stream()
                .map(entry -> {
                    String displayName;
                    if (ENTRY_TYPE_PRODUCT.equals(entry.getEntryType())) {
                        displayName = resolveProductName(entry.getProductId());
                    } else {
                        displayName = resolveMealName(entry.getMealId());
                    }
                    return DailyLogMapper.toResponse(entry, displayName);
                })
                .toList();
    }
}
