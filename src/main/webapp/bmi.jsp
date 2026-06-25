<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
    request.setAttribute("activePage", "bmi");
    request.setAttribute("pageTitle", "BMI Calculator");
    request.setAttribute("pageSubtitle", "Calculate and track your body mass index");
    Object bmiValueObj = request.getAttribute("bmiValue");
    String bmiValueDisplay = bmiValueObj != null ? String.valueOf(bmiValueObj) : "—";
    String bmiCategory = (String) request.getAttribute("bmiCategory");
    if (bmiCategory == null) {
        bmiCategory = "Calculate your BMI to see your category";
    }
    Object weightObj = request.getAttribute("weightKg");
    String weightDisplay = weightObj != null ? String.valueOf(weightObj) : "";
    Object heightObj = request.getAttribute("heightCm");
    String heightDisplay = heightObj != null ? String.valueOf(heightObj) : "";
    boolean hasServerBmi = bmiValueObj != null;
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Calyx — BMI</title>
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
                <h2 class="page-intro__title">Know your body. Track your health.</h2>
                <p class="page-intro__text">Enter your weight and height to calculate BMI and category.</p>
            </section>

            <section class="bmi-layout">
                <article class="card card--dark bmi-result" aria-live="polite">
                    <p class="stat-card__label" style="margin-bottom:16px;color:rgba(255,255,255,0.85)">Your BMI</p>
                    <p class="bmi-result__value" id="bmiValue"><%= bmiValueDisplay %></p>
                    <p class="bmi-result__category" id="bmiCategory"><%= bmiCategory %></p>

                    <div class="scale" id="bmiScale" aria-hidden="true">
                        <div class="scale__item" data-range="under">Underweight</div>
                        <div class="scale__item" data-range="normal">Normal</div>
                        <div class="scale__item" data-range="over">Overweight</div>
                        <div class="scale__item" data-range="obese">Obese</div>
                    </div>
                </article>

                <article class="card">
                    <div class="panel__header" style="padding:24px 24px 0">
                        <h3 class="panel__title">Calculate BMI</h3>
                    </div>
                    <form class="bmi-form" id="bmiForm">
                        <div class="field">
                            <label for="weightKg">Weight (kg)</label>
                            <input id="weightKg" name="weightKg" type="number" step="0.1" min="1"
                                   placeholder="e.g. 70" value="<%= weightDisplay %>" required>
                        </div>
                        <div class="field">
                            <label for="heightCm">Height (cm)</label>
                            <input id="heightCm" name="heightCm" type="number" step="1" min="1"
                                   placeholder="e.g. 175" value="<%= heightDisplay %>" required>
                        </div>
                        <div class="field field--full">
                            <button class="btn btn--primary" type="submit">Calculate BMI</button>
                        </div>
                    </form>
                    <% if (!hasServerBmi) { %>
                    <div class="empty-state hint-card">
                        Preview calculation runs in the browser. Connect a servlet to populate BMI from the backend.
                    </div>
                    <% } %>
                </article>
            </section>
        </main>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
<script>
    (function () {
        var form = document.getElementById('bmiForm');
        if (!form) return;

        function categoryFor(bmi) {
            if (bmi < 18.5) return { label: 'Underweight', range: 'under' };
            if (bmi < 25) return { label: 'Normal weight', range: 'normal' };
            if (bmi < 30) return { label: 'Overweight', range: 'over' };
            return { label: 'Obesity', range: 'obese' };
        }

        function updateScale(range) {
            document.querySelectorAll('.scale__item').forEach(function (item) {
                item.classList.toggle('is-active', item.dataset.range === range);
            });
        }

        form.addEventListener('submit', function (event) {
            event.preventDefault();
            var weight = parseFloat(document.getElementById('weightKg').value);
            var heightCm = parseFloat(document.getElementById('heightCm').value);
            if (!weight || !heightCm) return;

            var heightM = heightCm / 100;
            var bmi = weight / (heightM * heightM);
            var rounded = Math.round(bmi * 100) / 100;
            var result = categoryFor(rounded);

            document.getElementById('bmiValue').textContent = rounded;
            document.getElementById('bmiCategory').textContent = result.label;
            updateScale(result.range);
        });
    })();
</script>
</body>
</html>
