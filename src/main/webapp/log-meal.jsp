<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="java.util.List" %>

<%@ page import="com.calyx.dto.response.MealResponse" %>

<%

    request.setAttribute("activePage", "home");

    request.setAttribute("pageTitle", "Log meal");

    request.setAttribute("pageSubtitle", "Add a saved recipe to today's food log");

    List<MealResponse> meals = (List<MealResponse>) request.getAttribute("meals");

%>

<!DOCTYPE html>

<html lang="en">

<head>

    <title>Calyx — Log meal</title>

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

                <h2 class="page-intro__title">Log a meal</h2>

                <p class="page-intro__text">Choose a saved recipe and how many servings you had today.</p>

            </section>



            <% if (meals != null && !meals.isEmpty()) { %>

            <section class="card" style="max-width:520px">

                <form class="bmi-form" method="post" action="${pageContext.request.contextPath}/log-meal" style="padding:24px">

                    <div class="form-group">

                        <label for="mealId">Recipe</label>

                        <select id="mealId" name="mealId" required>

                            <% for (MealResponse meal : meals) { %>

                            <option value="<%= meal.id() %>">

                                <%= meal.name() %> — <%= meal.totalCalories() %> kcal

                            </option>

                            <% } %>

                        </select>

                    </div>

                    <div class="form-group">

                        <label for="servings">Servings</label>

                        <input type="number" id="servings" name="servings" min="0.25" step="0.25" value="1" required>

                    </div>

                    <div style="display:flex;gap:12px">

                        <button class="btn btn--primary" type="submit">Add to today</button>

                        <a class="btn btn--ghost" href="${pageContext.request.contextPath}/home">Cancel</a>

                    </div>

                </form>

            </section>

            <% } else { %>

            <div class="empty-state">

                No saved meals yet. <a href="${pageContext.request.contextPath}/meals">Create your first meal</a>.

            </div>

            <% } %>

        </main>

    </div>

</div>

<script src="${pageContext.request.contextPath}/js/app.js"></script>

</body>

</html>


