<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.calyx.dto.response.ProductResponse" %>
<%
    request.setAttribute("activePage", "products");
    request.setAttribute("pageTitle", "Log product");
    request.setAttribute("pageSubtitle", "Add a product to today's food log");
    ProductResponse product = (ProductResponse) request.getAttribute("product");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Calyx — Log product</title>
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
                <h2 class="page-intro__title">Log <%= product != null ? product.name() : "product" %></h2>
                <p class="page-intro__text">Enter the amount consumed. It will be added to today's daily log.</p>
            </section>

            <% if (product != null) { %>
            <section class="card" style="max-width:480px">
                <form class="bmi-form" method="post" action="${pageContext.request.contextPath}/log-product" style="padding:24px">
                    <input type="hidden" name="productId" value="<%= product.id() %>">
                    <div class="form-group">
                        <label for="grams">Amount (grams)</label>
                        <input type="number" id="grams" name="grams" min="1" step="1" value="100" required>
                    </div>
                    <p class="data-table__meta" style="margin-bottom:16px">
                        <%= product.caloriesPer100g() %> kcal per 100g
                    </p>
                    <div style="display:flex;gap:12px">
                        <button class="btn btn--primary" type="submit">Add to today</button>
                        <a class="btn btn--ghost" href="${pageContext.request.contextPath}/products">Cancel</a>
                    </div>
                </form>
            </section>
            <% } else { %>
            <div class="empty-state">Product not found.</div>
            <% } %>
        </main>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
