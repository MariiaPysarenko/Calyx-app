package com.calyx.servlet;

import com.calyx.dto.request.MealRequest;
import com.calyx.dto.response.MealResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/meals")
public class MealsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long userId = WebAppFactory.defaultUserId();
        List<MealResponse> meals = WebAppFactory.mealController().getMealsByUser(userId);
        req.setAttribute("meals", meals);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long userId = WebAppFactory.defaultUserId();
        String name = req.getParameter("name");

        if (name == null || name.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/meals");
            return;
        }

        MealResponse meal = WebAppFactory.mealController().createMeal(new MealRequest(userId, name.trim()));
        resp.sendRedirect(req.getContextPath() + "/meal?mealId=" + meal.id());
    }
}
