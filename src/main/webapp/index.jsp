<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="java.util.List" %>

<%@ page import="com.calyx.dto.response.DailyLogEntryResponse" %>

<%

    request.setAttribute("activePage", "home");

    String userName = (String) request.getAttribute("userName");

    if (userName == null || userName.isBlank()) {

        userName = "there";

    }

    request.setAttribute("userName", userName);

    request.setAttribute("pageTitle", "Dashboard");

    request.setAttribute("pageSubtitle", "Good morning, " + userName + " — here's your health overview");

    Object totalCaloriesObj = request.getAttribute("totalCalories");

    String totalCaloriesDisplay = totalCaloriesObj != null ? String.valueOf(totalCaloriesObj) : "—";

    Object totalEntriesObj = request.getAttribute("totalEntries");

    String totalEntriesDisplay = totalEntriesObj != null ? String.valueOf(totalEntriesObj) : "—";

    Object bmiValueObj = request.getAttribute("bmiValue");

    String bmiValueDisplay = bmiValueObj != null ? String.valueOf(bmiValueObj) : "—";

    String bmiCategory = (String) request.getAttribute("bmiCategory");

    if (bmiCategory == null) {

        bmiCategory = "Not calculated yet";

    }

    List<DailyLogEntryResponse> recentLog = (List<DailyLogEntryResponse>) request.getAttribute("recentLog");

    Object productCountObj = request.getAttribute("productCount");
    String productCountDisplay = productCountObj != null ? String.valueOf(productCountObj) : "—";

%>

<!DOCTYPE html>

<html lang="en">

<head>

    <title>Calyx — Dashboard</title>

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

                <h2 class="page-intro__title">Track calories. Build habits. Feel better.</h2>

                <p class="page-intro__text">Monitor your daily intake and BMI from one place.</p>

            </section>



            <section class="stats-grid" aria-label="Key metrics">

                <article class="card card--dark stat-card stat-card--highlight">

                    <p class="stat-card__label">Daily calories</p>

                    <p class="stat-card__value"><%= totalCaloriesDisplay %></p>

                    <span class="stat-card__meta">Logged today</span>

                </article>

                <article class="card stat-card">

                    <p class="stat-card__label">Today's entries</p>

                    <p class="stat-card__value"><%= totalEntriesDisplay %></p>

                    <span class="stat-card__meta">

                        <a class="panel__link" href="${pageContext.request.contextPath}/meals">My meals</a>

                    </span>

                </article>

                <article class="card stat-card">

                    <p class="stat-card__label">Latest BMI</p>

                    <p class="stat-card__value"><%= bmiValueDisplay %></p>

                    <span class="stat-card__meta"><%= bmiCategory %></span>

                </article>

                <article class="card stat-card">

                    <p class="stat-card__label">Products tracked</p>

                    <p class="stat-card__value"><%= productCountDisplay %></p>

                    <span class="stat-card__meta">

                        <a class="panel__link" href="${pageContext.request.contextPath}/products">View catalog</a>

                    </span>

                </article>

            </section>



            <section class="card table-card">

                <div class="panel__header" style="padding:24px 24px 0">

                    <h3 class="panel__title">Today's log</h3>

                    <a class="panel__link" href="${pageContext.request.contextPath}/insights">View insights</a>

                </div>

                <% if (recentLog != null && !recentLog.isEmpty()) { %>

                <table class="data-table">

                    <thead>

                    <tr>

                        <th>Item</th>

                        <th>Type</th>

                        <th>Amount</th>

                        <th>Calories</th>

                    </tr>

                    </thead>

                    <tbody>

                    <% for (DailyLogEntryResponse entry : recentLog) { %>

                    <tr>

                        <td>

                            <span class="data-table__name"><%= entry.displayName() %></span>

                        </td>

                        <td><span class="data-table__badge"><%= entry.entryType() %></span></td>

                        <td>

                            <% if ("PRODUCT".equals(entry.entryType()) && entry.grams() != null) { %>

                            <%= String.format("%.0f", entry.grams()) %> g

                            <% } else if ("MEAL".equals(entry.entryType()) && entry.servings() != null) { %>

                            <%= entry.servings() %> serving(s)

                            <% } else { %>

                            —

                            <% } %>

                        </td>

                        <td><%= entry.calories() %> kcal</td>

                    </tr>

                    <% } %>

                    </tbody>

                </table>

                <% } else { %>

                <div class="empty-state">

                    Nothing logged today. Add products from the catalog or log a meal from My meals.

                </div>

                <% } %>

            </section>

        </main>

    </div>

</div>

<script src="${pageContext.request.contextPath}/js/app.js"></script>

</body>

</html>


