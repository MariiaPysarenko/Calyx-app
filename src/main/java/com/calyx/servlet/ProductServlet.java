package com.calyx.servlet;

import com.calyx.dto.response.DailyLogEntryResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/home")
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long userId = WebAppFactory.defaultUserId();
        LocalDate today = LocalDate.now();

        req.setAttribute("userName", WebAppFactory.defaultUserName());
        req.setAttribute("totalCalories",
                WebAppFactory.calorieController().getDailyCalories(userId, today).totalCalories());

        List<DailyLogEntryResponse> dailyLog = WebAppFactory.mealController().getDailyLog(userId, today);
        req.setAttribute("totalEntries", dailyLog.size());
        req.setAttribute("recentLog", dailyLog);

        req.setAttribute("productCount", WebAppFactory.productController().getAll().size());

        var bmiHistory = WebAppFactory.bmiRepository().findByUserId(userId);
        if (!bmiHistory.isEmpty()) {
            var latest = bmiHistory.get(bmiHistory.size() - 1);
            req.setAttribute("bmiValue", latest.getBmiValue());
            req.setAttribute("bmiCategory", latest.getCategory());
        }

        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
