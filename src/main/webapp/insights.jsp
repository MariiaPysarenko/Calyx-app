<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.calyx.dto.response.ProductResponse" %>
<%
    request.setAttribute("activePage", "insights");
    request.setAttribute("pageTitle", "Insights");
    request.setAttribute("pageSubtitle", "Analytics and performance overview");
    Object growthObj = request.getAttribute("growth");
    String growthDisplay = growthObj != null ? String.valueOf(growthObj) : "—";
    Object avgOrderObj = request.getAttribute("avgCalories");
    String avgCaloriesDisplay = avgOrderObj != null ? String.valueOf(avgOrderObj) : "—";
    Object repeatRateObj = request.getAttribute("repeatRate");
    String repeatRateDisplay = repeatRateObj != null ? String.valueOf(repeatRateObj) : "—";
    List<ProductResponse> topProducts = (List<ProductResponse>) request.getAttribute("topProducts");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Calyx — Insights</title>
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
                <h2 class="page-intro__title">Track progress. Make smarter choices.</h2>
                <p class="page-intro__text">Review calorie trends, consistency, and your most-used products.</p>
            </section>

            <section class="stats-grid" aria-label="Key metrics">
                <article class="card stat-card">
                    <p class="stat-card__label">Growth</p>
                    <p class="stat-card__value"><%= growthDisplay %></p>
                    <span class="stat-card__meta">vs yesterday</span>
                </article>
                <article class="card stat-card">
                    <p class="stat-card__label">Today's calories</p>
                    <p class="stat-card__value"><%= avgCaloriesDisplay %></p>
                    <span class="stat-card__meta">Total logged</span>
                </article>
                <article class="card stat-card">
                    <p class="stat-card__label">Consistency</p>
                    <p class="stat-card__value"><%= repeatRateDisplay %></p>
                    <span class="stat-card__meta">Today's log</span>
                </article>
            </section>

            <section class="card table-card">
                <div class="panel__header" style="padding:24px 24px 0">
                    <h3 class="panel__title">Top products</h3>
                    <a class="panel__link" href="${pageContext.request.contextPath}/products">View all</a>
                </div>
                <% if (topProducts != null && !topProducts.isEmpty()) { %>
                <table class="data-table">
                    <thead>
                    <tr>
                        <th>Product</th>
                        <th>Category</th>
                        <th>Calories / 100g</th>
                        <th>Protein</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (ProductResponse product : topProducts) { %>
                    <tr>
                        <td>
                            <span class="data-table__name"><%= product.name() %></span>
                        </td>
                        <td><span class="data-table__badge"><%= product.category() %></span></td>
                        <td><%= product.caloriesPer100g() %> kcal</td>
                        <td><%= String.format("%.1f", product.proteinsPer100g()) %> g</td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
                <% } else { %>
                <div class="empty-state">
                    Log food to see your most-used products here.
                </div>
                <% } %>
            </section>
        </main>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
