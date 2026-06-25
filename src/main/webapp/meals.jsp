<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.calyx.dto.response.MealResponse" %>
<%
    request.setAttribute("activePage", "meals");
    request.setAttribute("pageTitle", "Meals");
    request.setAttribute("pageSubtitle", "Create and manage your custom recipes");
    List<MealResponse> meals = (List<MealResponse>) request.getAttribute("meals");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Calyx — Meals</title>
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
                <h2 class="page-intro__title">My meals</h2>
                <p class="page-intro__text">Build recipes from products, then add them to your daily log.</p>
            </section>

            <section class="card" style="max-width:520px;margin-bottom:24px">
                <form class="bmi-form" method="post" action="${pageContext.request.contextPath}/meals" style="padding:24px">
                    <h3 class="panel__title" style="margin-bottom:16px">Create new meal</h3>
                    <div class="form-group">
                        <label for="name">Meal name</label>
                        <input type="text" id="name" name="name" placeholder="e.g. Pasta with cheese" required>
                    </div>
                    <button class="btn btn--primary" type="submit">Create meal</button>
                </form>
            </section>

            <section class="card table-card">
                <div class="panel__header" style="padding:24px 24px 0">
                    <h3 class="panel__title">Saved meals</h3>
                </div>
                <% if (meals != null && !meals.isEmpty()) { %>
                <table class="data-table">
                    <thead>
                    <tr>
                        <th>Meal</th>
                        <th>Calories</th>
                        <th>Protein</th>
                        <th>Fats</th>
                        <th>Carbs</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (MealResponse meal : meals) { %>
                    <tr>
                        <td>
                            <span class="data-table__name"><%= meal.name() %></span>
                        </td>
                        <td><%= meal.totalCalories() %> kcal</td>
                        <td><%= String.format("%.1f", meal.totalProteins()) %> g</td>
                        <td><%= String.format("%.1f", meal.totalFats()) %> g</td>
                        <td><%= String.format("%.1f", meal.totalCarbs()) %> g</td>
                        <td>
                            <a class="btn btn--ghost" href="${pageContext.request.contextPath}/meal?mealId=<%= meal.id() %>">Edit</a>
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
                <% } else { %>
                <div class="empty-state">
                    No meals yet. Create your first recipe above.
                </div>
                <% } %>
            </section>
        </main>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
