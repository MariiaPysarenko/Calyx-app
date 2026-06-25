<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
    request.setAttribute("activePage", "more");
    request.setAttribute("pageTitle", "Settings");
    request.setAttribute("pageSubtitle", "Application preferences and quick links");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Calyx — Settings</title>
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>
<div class="dashboard">
    <%@ include file="/WEB-INF/jspf/prepare-layout.jspf" %>
    <%@ include file="/WEB-INF/jspf/sidebar.jspf" %>

    <div class="dashboard__main">
        <%@ include file="/WEB-INF/jspf/topbar.jspf" %>

        <main class="dashboard__content">
            <section class="card brand-card">
                <p class="brand-card__logo">Calyx</p>
                <p class="brand-card__tagline">Calorie tracking &amp; BMI — simple and mindful</p>
            </section>

            <section class="settings-grid">
                <a class="card menu-item" href="${pageContext.request.contextPath}/home">
                    <div class="menu-item__left">
                        <div class="menu-item__icon" aria-hidden="true">🏠</div>
                        <div>
                            <p class="menu-item__title">Dashboard</p>
                            <p class="menu-item__meta">Daily overview and recent activity</p>
                        </div>
                    </div>
                    <span aria-hidden="true">›</span>
                </a>
                <a class="card menu-item" href="${pageContext.request.contextPath}/products">
                    <div class="menu-item__left">
                        <div class="menu-item__icon" aria-hidden="true">🥗</div>
                        <div>
                            <p class="menu-item__title">Products</p>
                            <p class="menu-item__meta">Food database and macros</p>
                        </div>
                    </div>
                    <span aria-hidden="true">›</span>
                </a>
                <a class="card menu-item" href="${pageContext.request.contextPath}/insights">
                    <div class="menu-item__left">
                        <div class="menu-item__icon" aria-hidden="true">📊</div>
                        <div>
                            <p class="menu-item__title">Insights</p>
                            <p class="menu-item__meta">Analytics and trends</p>
                        </div>
                    </div>
                    <span aria-hidden="true">›</span>
                </a>
                <a class="card menu-item" href="${pageContext.request.contextPath}/bmi.jsp">
                    <div class="menu-item__left">
                        <div class="menu-item__icon" aria-hidden="true">⚖️</div>
                        <div>
                            <p class="menu-item__title">BMI Calculator</p>
                            <p class="menu-item__meta">Body mass index tracking</p>
                        </div>
                    </div>
                    <span aria-hidden="true">›</span>
                </a>
            </section>
        </main>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
