package com.calyx.view;

import com.calyx.controller.BmiController;
import com.calyx.controller.CalorieController;
import com.calyx.controller.MealController;
import com.calyx.controller.ProductController;
import com.calyx.controller.UserController;
import com.calyx.dto.request.BmiRequest;
import com.calyx.dto.request.CreateUserRequest;
import com.calyx.dto.request.DailyLogMealRequest;
import com.calyx.dto.request.DailyLogProductRequest;
import com.calyx.dto.request.MealIngredientRequest;
import com.calyx.dto.request.MealRequest;
import com.calyx.dto.request.ProductRequest;
import com.calyx.dto.response.BmiResponse;
import com.calyx.dto.response.DailyCaloriesResponse;
import com.calyx.dto.response.DailyLogEntryResponse;
import com.calyx.dto.response.MealIngredientResponse;
import com.calyx.dto.response.MealResponse;
import com.calyx.dto.response.ProductResponse;
import com.calyx.dto.response.UserResponse;
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

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ConsoleView {

    private final Scanner scanner = new Scanner(System.in);
    private final UserController userController;
    private final ProductController productController;
    private final MealController mealController;
    private final BmiController bmiController;
    private final CalorieController calorieController;

    public ConsoleView() {
        var userRepository = new UserRepositoryImpl();
        var productRepository = new ProductRepositoryImpl();
        var mealRepository = new MealRepositoryImpl();
        var mealIngredientRepository = new MealIngredientRepositoryImpl();
        var dailyLogRepository = new DailyLogRepositoryImpl();
        var bmiRepository = new BmiRepositoryImpl();

        userController = new UserController(new UserServiceImpl(userRepository));
        productController = new ProductController(new ProductServiceImpl(productRepository));
        mealController = new MealController(
                new MealServiceImpl(mealRepository, userRepository),
                new MealIngredientServiceImpl(mealIngredientRepository, mealRepository, productRepository),
                new DailyLogServiceImpl(dailyLogRepository, productRepository, mealRepository, userRepository)
        );
        bmiController = new BmiController(new BmiServiceImpl(bmiRepository));
        calorieController = new CalorieController(
                new CalorieServiceImpl(dailyLogRepository, userRepository)
        );
    }

    public void start() {
        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            try {
                running = handleChoice(choice);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("=== Calyx ===");
        System.out.println("1. Create user");
        System.out.println("2. List users");
        System.out.println("3. Create product");
        System.out.println("4. List products");
        System.out.println("5. Create meal (recipe)");
        System.out.println("6. List meals by user");
        System.out.println("7. Add ingredient to meal");
        System.out.println("8. List meal ingredients");
        System.out.println("9. Log product to daily log");
        System.out.println("10. Log meal to daily log");
        System.out.println("11. View daily log");
        System.out.println("12. Calculate BMI");
        System.out.println("13. Daily calories");
        System.out.println("0. Exit");
        System.out.print("Choose option: ");
    }

    private boolean handleChoice(String choice) {
        return switch (choice) {
            case "1" -> {
                createUser();
                yield true;
            }
            case "2" -> {
                listUsers();
                yield true;
            }
            case "3" -> {
                createProduct();
                yield true;
            }
            case "4" -> {
                listProducts();
                yield true;
            }
            case "5" -> {
                createMeal();
                yield true;
            }
            case "6" -> {
                listMeals();
                yield true;
            }
            case "7" -> {
                addIngredient();
                yield true;
            }
            case "8" -> {
                listIngredients();
                yield true;
            }
            case "9" -> {
                logProduct();
                yield true;
            }
            case "10" -> {
                logMeal();
                yield true;
            }
            case "11" -> {
                viewDailyLog();
                yield true;
            }
            case "12" -> {
                calculateBmi();
                yield true;
            }
            case "13" -> {
                showDailyCalories();
                yield true;
            }
            case "0" -> false;
            default -> {
                System.out.println("Unknown option");
                yield true;
            }
        };
    }

    private void createUser() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Age: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Weight (kg): ");
        double weight = Double.parseDouble(scanner.nextLine());
        System.out.print("Height (cm): ");
        int height = Integer.parseInt(scanner.nextLine());
        System.out.print("Goal: ");
        String goal = scanner.nextLine();

        UserResponse user = userController.create(
                new CreateUserRequest(name, email, password, age, weight, height, goal)
        );
        System.out.println("User created: " + user);
    }

    private void listUsers() {
        List<UserResponse> users = userController.getAll();
        if (users.isEmpty()) {
            System.out.println("No users found");
            return;
        }
        users.forEach(System.out::println);
    }

    private void createProduct() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Calories per 100g: ");
        int calories = Integer.parseInt(scanner.nextLine());
        System.out.print("Proteins per 100g: ");
        double proteins = Double.parseDouble(scanner.nextLine());
        System.out.print("Fats per 100g: ");
        double fats = Double.parseDouble(scanner.nextLine());
        System.out.print("Carbs per 100g: ");
        double carbs = Double.parseDouble(scanner.nextLine());

        ProductResponse product = productController.create(
                new ProductRequest(name, "other", calories, proteins, fats, carbs)
        );
        System.out.println("Product created: " + product);
    }

    private void listProducts() {
        List<ProductResponse> products = productController.getAll();
        if (products.isEmpty()) {
            System.out.println("No products found");
            return;
        }
        products.forEach(System.out::println);
    }

    private void createMeal() {
        System.out.print("User id: ");
        Long userId = Long.parseLong(scanner.nextLine());
        System.out.print("Recipe name: ");
        String name = scanner.nextLine();

        MealResponse meal = mealController.createMeal(new MealRequest(userId, name));
        System.out.println("Meal created: " + meal);
    }

    private void listMeals() {
        System.out.print("User id: ");
        Long userId = Long.parseLong(scanner.nextLine());

        List<MealResponse> meals = mealController.getMealsByUser(userId);
        if (meals.isEmpty()) {
            System.out.println("No meals found");
            return;
        }
        meals.forEach(System.out::println);
    }

    private void addIngredient() {
        System.out.print("Meal id: ");
        Long mealId = Long.parseLong(scanner.nextLine());
        System.out.print("Product id: ");
        Long productId = Long.parseLong(scanner.nextLine());
        System.out.print("Grams: ");
        double grams = Double.parseDouble(scanner.nextLine());

        MealIngredientResponse ingredient = mealController.addIngredient(
                new MealIngredientRequest(mealId, productId, grams)
        );
        System.out.println("Ingredient added: " + ingredient);
    }

    private void listIngredients() {
        System.out.print("Meal id: ");
        Long mealId = Long.parseLong(scanner.nextLine());

        List<MealIngredientResponse> ingredients = mealController.getIngredientsByMeal(mealId);
        if (ingredients.isEmpty()) {
            System.out.println("No ingredients found");
            return;
        }
        ingredients.forEach(System.out::println);
    }

    private void logProduct() {
        System.out.print("User id: ");
        Long userId = Long.parseLong(scanner.nextLine());
        System.out.print("Product id: ");
        Long productId = Long.parseLong(scanner.nextLine());
        System.out.print("Grams: ");
        double grams = Double.parseDouble(scanner.nextLine());
        System.out.print("Date (yyyy-MM-dd, blank for today): ");
        String dateInput = scanner.nextLine().trim();
        LocalDate date = dateInput.isEmpty() ? LocalDate.now() : LocalDate.parse(dateInput);

        DailyLogEntryResponse entry = mealController.logProduct(
                new DailyLogProductRequest(userId, productId, grams, date)
        );
        System.out.println("Logged product: " + entry);
    }

    private void logMeal() {
        System.out.print("User id: ");
        Long userId = Long.parseLong(scanner.nextLine());
        System.out.print("Meal id: ");
        Long mealId = Long.parseLong(scanner.nextLine());
        System.out.print("Servings: ");
        double servings = Double.parseDouble(scanner.nextLine());
        System.out.print("Date (yyyy-MM-dd, blank for today): ");
        String dateInput = scanner.nextLine().trim();
        LocalDate date = dateInput.isEmpty() ? LocalDate.now() : LocalDate.parse(dateInput);

        DailyLogEntryResponse entry = mealController.logMeal(
                new DailyLogMealRequest(userId, mealId, servings, date)
        );
        System.out.println("Logged meal: " + entry);
    }

    private void viewDailyLog() {
        System.out.print("User id: ");
        Long userId = Long.parseLong(scanner.nextLine());
        System.out.print("Date (yyyy-MM-dd): ");
        try {
            LocalDate date = LocalDate.parse(scanner.nextLine());
            List<DailyLogEntryResponse> entries = mealController.getDailyLog(userId, date);
            if (entries.isEmpty()) {
                System.out.println("No entries for this date");
                return;
            }
            entries.forEach(System.out::println);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format");
        }
    }

    private void calculateBmi() {
        System.out.print("User id: ");
        Long userId = Long.parseLong(scanner.nextLine());
        System.out.print("Weight (kg): ");
        double weight = Double.parseDouble(scanner.nextLine());
        System.out.print("Height (cm): ");
        int height = Integer.parseInt(scanner.nextLine());

        BmiResponse response = bmiController.calculate(new BmiRequest(userId, weight, height));
        System.out.println("BMI: " + response.bmiValue() + ", category: " + response.category());
    }

    private void showDailyCalories() {
        System.out.print("User id: ");
        Long userId = Long.parseLong(scanner.nextLine());
        System.out.print("Date (yyyy-MM-dd): ");
        try {
            LocalDate date = LocalDate.parse(scanner.nextLine());
            DailyCaloriesResponse response = calorieController.getDailyCalories(userId, date);
            System.out.println("Total calories: " + response.totalCalories());
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format");
        }
    }
}
