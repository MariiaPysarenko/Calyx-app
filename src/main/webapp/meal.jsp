<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.calyx.dto.response.MealResponse" %>
<%@ page import="com.calyx.dto.response.MealIngredientResponse" %>
<%@ page import="com.calyx.dto.response.ProductResponse" %>
<%
    request.setAttribute("activePage", "meals");
    request.setAttribute("pageTitle", "Meal editor");
    MealResponse meal = (MealResponse) request.getAttribute("meal");
    List<MealIngredientResponse> ingredients = (List<MealIngredientResponse>) request.getAttribute("ingredients");
    List<ProductResponse> products = (List<ProductResponse>) request.getAttribute("products");
    Map<Long, String> productNames = (Map<Long, String>) request.getAttribute("productNames");
    if (meal != null) {
        request.setAttribute("pageSubtitle", meal.name() + " — add ingredients and log to today");
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Calyx — <%= meal != null ? meal.name() : "Meal" %></title>
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>
<div class="dashboard">
    <%@ include file="/WEB-INF/jspf/prepare-layout.jspf" %>
    <%@ include file="/WEB-INF/jspf/sidebar.jspf" %>

    <div class="dashboard__main">
        <%@ include file="/WEB-INF/jspf/topbar.jspf" %>

        <main class="dashboard__content">
            <% if (meal != null) { %>
            <section class="page-intro">
                <h2 class="page-intro__title"><%= meal.name() %></h2>
                <p class="page-intro__text">
                    Total: <%= meal.totalCalories() %> kcal ·
                    P <%= String.format("%.1f", meal.totalProteins()) %> g ·
                    F <%= String.format("%.1f", meal.totalFats()) %> g ·
                    C <%= String.format("%.1f", meal.totalCarbs()) %> g
                </p>
            </section>

            <div class="dashboard-grid" style="grid-template-columns:1fr 1fr">
                <section class="card table-card">
                    <div class="panel__header" style="padding:24px 24px 0">
                        <h3 class="panel__title">Ingredients</h3>
                    </div>
                    <% if (ingredients != null && !ingredients.isEmpty()) { %>
                    <table class="data-table">
                        <thead>
                        <tr>
                            <th>Product</th>
                            <th>Amount</th>
                            <th>Calories</th>
                        </tr>
                        </thead>
                        <tbody>
                        <% for (MealIngredientResponse ingredient : ingredients) {
                            String productName = productNames != null
                                    ? productNames.getOrDefault(ingredient.productId(), "Product")
                                    : "Product";
                        %>
                        <tr>
                            <td><span class="data-table__name"><%= productName %></span></td>
                            <td><%= String.format("%.0f", ingredient.grams()) %> g</td>
                            <td><%= ingredient.calories() %> kcal</td>
                        </tr>
                        <% } %>
                        </tbody>
                    </table>
                    <% } else { %>
                    <div class="empty-state">No ingredients yet. Add products from the form.</div>
                    <% } %>
                </section>

                <section class="card">
                    <form class="bmi-form" method="post" action="${pageContext.request.contextPath}/meal" style="padding:24px">
                        <input type="hidden" name="mealId" value="<%= meal.id() %>">
                        <h3 class="panel__title" style="margin-bottom:16px">Add ingredient</h3>
                        <div class="form-group">
                            <label for="productId">Product</label>
                            <select id="productId" name="productId" required>
                                <% if (products != null) {
                                    for (ProductResponse product : products) { %>
                                <option value="<%= product.id() %>"><%= product.name() %></option>
                                <% }
                                } %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="grams">Grams</label>
                            <input type="number" id="grams" name="grams" min="1" step="1" value="100" required>
                        </div>
                        <button class="btn btn--primary" type="submit">Add ingredient</button>
                    </form>

                    <form class="bmi-form" method="post" action="${pageContext.request.contextPath}/meal"
                          style="padding:0 24px 24px;border-top:1px solid var(--border)">
                        <input type="hidden" name="mealId" value="<%= meal.id() %>">
                        <input type="hidden" name="action" value="log">
                        <h3 class="panel__title" style="margin:24px 0 16px">Add to today's log</h3>
                        <div class="form-group">
                            <label for="servings">Servings</label>
                            <input type="number" id="servings" name="servings" min="0.25" step="0.25" value="1" required>
                        </div>
                        <div style="display:flex;gap:12px">
                            <button class="btn btn--primary" type="submit">Log today</button>
                            <a class="btn btn--ghost" href="${pageContext.request.contextPath}/meals">Back to meals</a>
                        </div>
                    </form>
                </section>
            </div>
            <% } else { %>
            <div class="empty-state">Meal not found. <a href="${pageContext.request.contextPath}/meals">Go back</a></div>
            <% } %>
        </main>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
