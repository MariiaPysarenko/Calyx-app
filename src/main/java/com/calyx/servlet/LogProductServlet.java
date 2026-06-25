package com.calyx.servlet;

import com.calyx.dto.request.DailyLogMealRequest;
import com.calyx.dto.request.DailyLogProductRequest;
import com.calyx.dto.response.ProductResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/log-product")
public class LogProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productIdParam = req.getParameter("productId");
        if (productIdParam == null || productIdParam.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/products");
            return;
        }

        long productId = Long.parseLong(productIdParam);
        ProductResponse product = WebAppFactory.productController().getById(productId);
        req.setAttribute("product", product);
        req.getRequestDispatcher("log-product.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long userId = WebAppFactory.defaultUserId();
        long productId = Long.parseLong(req.getParameter("productId"));
        double grams = Double.parseDouble(req.getParameter("grams"));

        WebAppFactory.mealController().logProduct(
                new DailyLogProductRequest(userId, productId, grams, LocalDate.now())
        );
        resp.sendRedirect(req.getContextPath() + "/home");
    }
}
