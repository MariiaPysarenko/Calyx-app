<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.calyx.dto.response.ProductResponse" %>
<%
    request.setAttribute("activePage", "products");
    request.setAttribute("pageTitle", "Products");
    request.setAttribute("pageSubtitle", "Manage your food database and nutritional values");
    List<ProductResponse> products = (List<ProductResponse>) request.getAttribute("products");
    String searchQuery = (String) request.getAttribute("searchQuery");
    String activeCategory = (String) request.getAttribute("activeCategory");
    if (searchQuery == null) searchQuery = "";
    if (activeCategory == null) activeCategory = "";
    int visibleCount = products != null ? products.size() : 0;
    String baseUrl = request.getContextPath() + "/products";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Calyx — Products</title>
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>
<div class="dashboard">
    <%@ include file="/WEB-INF/jspf/prepare-layout.jspf" %>
    <%@ include file="/WEB-INF/jspf/sidebar.jspf" %>

    <div class="dashboard__main">
        <%@ include file="/WEB-INF/jspf/topbar.jspf" %>

        <main class="dashboard__content">
            <section class="page-intro">
                <h2 class="page-intro__title">Product catalog</h2>
                <p class="page-intro__text">Browse and manage foods with calories and macros per 100g.</p>
            </section>

            <div class="toolbar">
                <div class="toolbar__left">
                    <form class="search-bar" method="get" action="<%= baseUrl %>">
                        <% if (!activeCategory.isBlank()) { %>
                        <input type="hidden" name="category" value="<%= activeCategory %>">
                        <% } %>
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="#84A98C" stroke-width="2" aria-hidden="true">
                            <circle cx="11" cy="11" r="7"/><path d="M20 20l-3.5-3.5"/>
                        </svg>
                        <input type="search" name="q" placeholder="Search products..." value="<%= searchQuery %>">
                    </form>
                    <div class="filter-bar" role="tablist" aria-label="Product categories">
                        <%
                            String[][] filters = {
                                {"", "All"},
                                {"fruits", "Fruits"},
                                {"vegetables", "Vegetables"},
                                {"protein", "Protein"},
                                {"grains", "Grains"}
                            };
                            for (String[] filter : filters) {
                                String catValue = filter[0];
                                String catLabel = filter[1];
                                boolean isActive = activeCategory.equalsIgnoreCase(catValue);
                                String href = baseUrl + "?";
                                if (!catValue.isBlank()) href += "category=" + catValue + "&";
                                if (!searchQuery.isBlank()) href += "q=" + java.net.URLEncoder.encode(searchQuery, "UTF-8");
                                if (href.endsWith("?") || href.endsWith("&")) {
                                    href = href.replaceAll("[?&]$", "");
                                }
                        %>
                        <a class="filter-chip<%= isActive ? " is-active" : "" %>"
                           href="<%= href %>"><%= catLabel %></a>
                        <% } %>
                    </div>
                </div>
                <div class="toolbar__right">
                    <span class="data-table__badge"><%= visibleCount %> products</span>
                </div>
            </div>

            <section class="card table-card">
                <% if (products != null && !products.isEmpty()) { %>
                <table class="data-table">
                    <thead>
                    <tr>
                        <th>Product</th>
                        <th>Category</th>
                        <th>Calories</th>
                        <th>Protein</th>
                        <th>Fats</th>
                        <th>Carbs</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (ProductResponse product : products) { %>
                    <tr>
                        <td>
                            <span class="data-table__name"><%= product.name() %></span>
                        </td>
                        <td><span class="data-table__badge"><%= product.category() %></span></td>
                        <td><%= product.caloriesPer100g() %> kcal</td>
                        <td><%= String.format("%.1f", product.proteinsPer100g()) %> g</td>
                        <td><%= String.format("%.1f", product.fatsPer100g()) %> g</td>
                        <td><%= String.format("%.1f", product.carbsPer100g()) %> g</td>
                        <td>
                            <a class="btn btn--add"
                               href="${pageContext.request.contextPath}/log-product?productId=<%= product.id() %>"
                               aria-label="Log <%= product.name() %> to today">+</a>
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
                <% } else { %>
                <div class="empty-state">
                    No products match your filters.
                </div>
                <% } %>
            </section>
        </main>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
