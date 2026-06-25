package com.calyx.servlet;

import com.calyx.dto.response.DailyCaloriesResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/insights")
public class InsightsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long userId = WebAppFactory.defaultUserId();
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        DailyCaloriesResponse todayCalories = WebAppFactory.calorieController().getDailyCalories(userId, today);
        DailyCaloriesResponse yesterdayCalories = WebAppFactory.calorieController().getDailyCalories(userId, yesterday);

        req.setAttribute("avgCalories", todayCalories.totalCalories());

        if (yesterdayCalories.totalCalories() > 0) {
            int change = (int) Math.round(
                    ((double) (todayCalories.totalCalories() - yesterdayCalories.totalCalories())
                            / yesterdayCalories.totalCalories()) * 100
            );
            req.setAttribute("growth", change + "%");
        }

        int entryCount = WebAppFactory.mealController().getDailyLog(userId, today).size();
        req.setAttribute("repeatRate", entryCount + " entries");
        req.setAttribute("topProducts", WebAppFactory.productController().getAll().stream().limit(5).toList());

        req.getRequestDispatcher("insights.jsp").forward(req, resp);
    }
}
