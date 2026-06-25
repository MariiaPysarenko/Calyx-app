package com.calyx.servlet;

import com.calyx.dto.response.ProductResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/products")
public class ProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchQuery = req.getParameter("q");
        String category = req.getParameter("category");

        List<ProductResponse> products = WebAppFactory.productController().getAll().stream()
                .filter(product -> matchesSearch(product, searchQuery))
                .filter(product -> matchesCategory(product, category))
                .toList();

        req.setAttribute("products", products);
        req.setAttribute("searchQuery", searchQuery == null ? "" : searchQuery);
        req.setAttribute("activeCategory", category == null ? "" : category);
        req.getRequestDispatcher("products.jsp").forward(req, resp);
    }

    private boolean matchesSearch(ProductResponse product, String query) {
        if (query == null || query.isBlank()) {
            return true;
        }
        return product.name().toLowerCase().contains(query.trim().toLowerCase());
    }

    private boolean matchesCategory(ProductResponse product, String category) {
        if (category == null || category.isBlank()) {
            return true;
        }
        return category.equalsIgnoreCase(product.category());
    }
}
