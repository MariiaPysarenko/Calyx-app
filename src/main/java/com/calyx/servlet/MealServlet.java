package com.calyx.servlet;

import com.calyx.dto.request.DailyLogMealRequest;
import com.calyx.dto.request.MealIngredientRequest;
import com.calyx.dto.response.MealIngredientResponse;
import com.calyx.dto.response.MealResponse;
import com.calyx.dto.response.ProductResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/meal")
public class MealServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mealIdParam = req.getParameter("mealId");
        if (mealIdParam == null || mealIdParam.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/meals");
            return;
        }

        long mealId = Long.parseLong(mealIdParam);
        MealResponse meal = WebAppFactory.mealController().getMealById(mealId);
        List<MealIngredientResponse> ingredients = WebAppFactory.mealController().getIngredientsByMeal(mealId);
        List<ProductResponse> products = WebAppFactory.productController().getAll();

        Map<Long, String> productNames = new HashMap<>();
        for (ProductResponse product : products) {
            productNames.put(product.id(), product.name());
        }

        req.setAttribute("meal", meal);
        req.setAttribute("ingredients", ingredients);
        req.setAttribute("products", products);
        req.setAttribute("productNames", productNames);
        req.getRequestDispatcher("meal.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long mealId = Long.parseLong(req.getParameter("mealId"));
        String action = req.getParameter("action");

        if ("log".equals(action)) {
            long userId = WebAppFactory.defaultUserId();
            double servings = Double.parseDouble(req.getParameter("servings"));
            WebAppFactory.mealController().logMeal(
                    new DailyLogMealRequest(userId, mealId, servings, LocalDate.now())
            );
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        long productId = Long.parseLong(req.getParameter("productId"));
        double grams = Double.parseDouble(req.getParameter("grams"));
        WebAppFactory.mealController().addIngredient(new MealIngredientRequest(mealId, productId, grams));
        resp.sendRedirect(req.getContextPath() + "/meal?mealId=" + mealId);
    }
}
