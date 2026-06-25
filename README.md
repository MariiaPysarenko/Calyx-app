# Calyx

Calyx is a calorie-tracking and health dashboard built with Java servlets, JSP, and PostgreSQL. Users can browse a product catalog, log food to a daily diary, create custom meal recipes from ingredients, and track BMI.

## Tech stack

- Java 17
- Maven (WAR)
- Apache Tomcat 11 (Jakarta Servlet 6)
- PostgreSQL
- JSP + CSS (desktop dashboard UI)
- JUnit 4 + Mockito (unit tests)

## Prerequisites

Install before running the app:

| Tool | Version |
|------|---------|
| JDK | 17+ |
| Maven | 3.8+ |
| PostgreSQL | 15+ |
| Apache Tomcat | 11.x |

Make sure `java`, `mvn`, and `psql` are available in your terminal (or use the Maven/Tomcat bundled with IntelliJ IDEA).

## 1. Clone the repository

```bash
git clone https://github.com/MariiaPysarenko/Calyx-app.git
cd Calyx-app
```

## 2. Configure the database

### Create the database

```bash
psql -U postgres -c "CREATE DATABASE calyx_db;"
```

### Apply schema and seed data

**Option A — fresh install (recommended for first run)**

```bash
psql -U postgres -d calyx_db -f db.sql
```

**Option B — migrations + demo data (Windows PowerShell)**

```powershell
.\seed.ps1
```

`seed.ps1` runs, in order:

- `migrate-users.sql`
- `migrate-meal-model.sql`
- `migrate-product-category.sql`
- `seed.sql`

Demo user: `maria@calyx.local` (used by the web UI as the default user).

### Connection settings

Edit `src/main/resources/application.properties`:

```properties
db.url=jdbc:postgresql://localhost:5432/calyx_db
db.user=postgres
db.password=YOUR_PASSWORD
```

## 3. Build the project

```bash
mvn clean package
```

The WAR file is produced at:

```
target/calyx.war
```

## 4. Run tests (optional)

```bash
mvn test
```

## 5. Deploy to Tomcat

1. Copy `target/calyx.war` to Tomcat’s `webapps/` folder.
2. Start Tomcat.
3. Wait until the app is deployed (Tomcat unpacks the WAR automatically).

**IntelliJ IDEA:** configure a Tomcat 11 local run configuration, deploy the `calyx:war` artifact, and set the application context to `/` (root).

## 6. Open the app

With context path `/`:

| Page | URL |
|------|-----|
| Dashboard | http://localhost:8080/home |
| Products | http://localhost:8080/products |
| Meals (recipes) | http://localhost:8080/meals |
| Insights | http://localhost:8080/insights |
| BMI calculator | http://localhost:8080/bmi.jsp |

> Open servlet URLs (`/home`, `/products`, …), not JSP files directly — servlets load data from the database.

### Quick workflow

1. **Products** — browse the catalog, filter by category, click `+` to log a product to today’s diary.
2. **Meals** — create a recipe, add ingredients, then log servings to the daily diary.
3. **Dashboard** — view today’s calories and food log.

## 7. Console mode (optional)

Run the CLI admin tool without Tomcat:

```bash
mvn compile exec:java -Dexec.mainClass="com.calyx.Main"
```

Or run `com.calyx.Main` from your IDE. The console supports users, products, meals, ingredients, daily log, BMI, and calorie totals.

## Project structure

```
src/main/java/com/calyx/
  controller/    HTTP-facing orchestration
  service/       Business logic
  repository/    JDBC data access
  servlet/       Web endpoints
  model/         Domain entities
  dto/           Request/response records
  mapper/        Entity ↔ DTO mapping
  util/          Validation, nutrition math, DB connection

src/main/webapp/
  *.jsp          UI pages
  css/           Dashboard styles
  WEB-INF/jspf/  Layout fragments (sidebar, topbar)

db.sql                    Full schema (new database)
migrate-*.sql             Incremental migrations
seed.sql                  Demo products, meals, daily log
```

## Troubleshooting

| Problem | Solution |
|---------|----------|
| `relation "daily_log" does not exist` | Run `migrate-meal-model.sql` or `.\seed.ps1` |
| Products missing `category` column | Run `migrate-product-category.sql` or `.\seed.ps1` |
| Empty pages / no data | Use servlet URLs (`/home`), not raw JSP paths |
| `No suitable driver` | Rebuild and redeploy the WAR; PostgreSQL driver is packaged in the WAR |
| JDBC connection refused | Check PostgreSQL is running and `application.properties` credentials |

## License

MIT License (or to be added)
