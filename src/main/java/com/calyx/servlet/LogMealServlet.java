package com.calyx.servlet;

import com.calyx.dto.request.DailyLogMealRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/log-meal")
public class LogMealServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long userId = WebAppFactory.defaultUserId();
        req.setAttribute("meals", WebAppFactory.mealController().getMealsByUser(userId));
        req.getRequestDispatcher("log-meal.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long userId = WebAppFactory.defaultUserId();
        long mealId = Long.parseLong(req.getParameter("mealId"));
        double servings = Double.parseDouble(req.getParameter("servings"));

        WebAppFactory.mealController().logMeal(
                new DailyLogMealRequest(userId, mealId, servings, LocalDate.now())
        );
        resp.sendRedirect(req.getContextPath() + "/home");
    }
}
