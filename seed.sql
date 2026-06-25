-- Calyx seed data: products, user, recipes (meals), and daily log entries

-- Safe to re-run: clears demo meals and daily log, then re-inserts sample rows



ALTER TABLE users ADD COLUMN IF NOT EXISTS password VARCHAR(255) NOT NULL DEFAULT '';

ALTER TABLE users ADD COLUMN IF NOT EXISTS age INT NOT NULL DEFAULT 25;

ALTER TABLE users ADD COLUMN IF NOT EXISTS weight_kg DOUBLE PRECISION NOT NULL DEFAULT 70;

ALTER TABLE users ADD COLUMN IF NOT EXISTS height_cm INT NOT NULL DEFAULT 170;

ALTER TABLE users ADD COLUMN IF NOT EXISTS goal VARCHAR(50) NOT NULL DEFAULT 'maintain';



UPDATE users

SET password = 'seed',

    age = 28,

    weight_kg = 65,

    height_cm = 168,

    goal = 'lose_weight'

WHERE email = 'maria@calyx.local';



INSERT INTO product (name, category, calories_per_100g, proteins_per_100g, fats_per_100g, carbs_per_100g)

SELECT v.name, v.category, v.calories, v.proteins, v.fats, v.carbs

FROM (VALUES

    ('Chicken breast', 'protein', 165, 31.0, 3.6, 0.0),

    ('Brown rice', 'grains', 111, 2.6, 0.9, 23.0),

    ('Broccoli', 'vegetables', 34, 2.8, 0.4, 7.0),

    ('Greek yogurt', 'protein', 59, 10.0, 0.4, 3.6),

    ('Banana', 'fruits', 89, 1.1, 0.3, 22.8),

    ('Oatmeal', 'grains', 68, 2.4, 1.4, 12.0),

    ('Salmon fillet', 'protein', 208, 20.0, 13.0, 0.0),

    ('Avocado', 'vegetables', 160, 2.0, 14.7, 8.5),

    ('Cherry tomatoes', 'vegetables', 18, 0.9, 0.2, 3.9),

    ('Whole wheat bread', 'grains', 247, 13.0, 3.4, 41.0),

    ('Cottage cheese', 'protein', 98, 11.0, 4.3, 3.4),

    ('Apple', 'fruits', 52, 0.3, 0.2, 13.8),

    ('Eggs', 'protein', 155, 13.0, 11.0, 1.1),

    ('Spinach', 'vegetables', 23, 2.9, 0.4, 3.6),

    ('Almonds', 'protein', 579, 21.0, 50.0, 22.0)

) AS v(name, category, calories, proteins, fats, carbs)

WHERE NOT EXISTS (SELECT 1 FROM product p WHERE p.name = v.name);



INSERT INTO users (name, email, password, age, weight_kg, height_cm, goal)

SELECT 'Maria', 'maria@calyx.local', 'seed', 28, 65, 168, 'lose_weight'

WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'maria@calyx.local');



DELETE FROM daily_log

WHERE user_id = (SELECT id FROM users WHERE email = 'maria@calyx.local');



DELETE FROM meal_ingredient

WHERE meal_id IN (SELECT id FROM meal WHERE user_id = (SELECT id FROM users WHERE email = 'maria@calyx.local'));



DELETE FROM meal

WHERE user_id = (SELECT id FROM users WHERE email = 'maria@calyx.local');



-- Recipes

INSERT INTO meal (user_id, name, total_calories, total_proteins, total_fats, total_carbs)

SELECT u.id, v.name, 0, 0, 0, 0

FROM users u

CROSS JOIN (VALUES

    ('Chicken rice bowl'),

    ('Greek yogurt breakfast'),

    ('Salmon salad')

) AS v(name)

WHERE u.email = 'maria@calyx.local';



-- Ingredients (nutrition calculated per 100g * grams / 100)

INSERT INTO meal_ingredient (meal_id, product_id, grams, calories, proteins, fats, carbs)

SELECT m.id, p.id, d.grams,

       CAST(ROUND(p.calories_per_100g * d.grams / 100.0) AS INTEGER),

       ROUND((p.proteins_per_100g * d.grams / 100.0)::numeric, 2),

       ROUND((p.fats_per_100g * d.grams / 100.0)::numeric, 2),

       ROUND((p.carbs_per_100g * d.grams / 100.0)::numeric, 2)

FROM users u

JOIN meal m ON m.user_id = u.id

JOIN (VALUES

    ('Chicken rice bowl', 'Chicken breast', 150),

    ('Chicken rice bowl', 'Brown rice', 180),

    ('Chicken rice bowl', 'Broccoli', 100),

    ('Greek yogurt breakfast', 'Oatmeal', 80),

    ('Greek yogurt breakfast', 'Banana', 120),

    ('Greek yogurt breakfast', 'Greek yogurt', 150),

    ('Salmon salad', 'Salmon fillet', 160),

    ('Salmon salad', 'Spinach', 80),

    ('Salmon salad', 'Avocado', 70),

    ('Salmon salad', 'Cherry tomatoes', 90)

) AS d(meal_name, product_name, grams)

    ON m.name = d.meal_name

JOIN product p ON p.name = d.product_name

WHERE u.email = 'maria@calyx.local';



UPDATE meal m

SET total_calories = agg.calories,

    total_proteins = agg.proteins,

    total_fats = agg.fats,

    total_carbs = agg.carbs

FROM (

    SELECT meal_id,

           SUM(calories) AS calories,

           SUM(proteins) AS proteins,

           SUM(fats) AS fats,

           SUM(carbs) AS carbs

    FROM meal_ingredient

    GROUP BY meal_id

) AS agg

WHERE m.id = agg.meal_id;



-- Daily log: today and yesterday

INSERT INTO daily_log (user_id, log_date, entry_type, product_id, meal_id, grams, servings, calories, proteins, fats, carbs)

SELECT u.id, d.log_date::date, 'PRODUCT', p.id, NULL, d.grams, NULL,

       CAST(ROUND(p.calories_per_100g * d.grams / 100.0) AS INTEGER),

       ROUND((p.proteins_per_100g * d.grams / 100.0)::numeric, 2),

       ROUND((p.fats_per_100g * d.grams / 100.0)::numeric, 2),

       ROUND((p.carbs_per_100g * d.grams / 100.0)::numeric, 2)

FROM users u

CROSS JOIN (VALUES

    (CURRENT_DATE, 'Apple', 180),

    (CURRENT_DATE, 'Almonds', 30),

    (CURRENT_DATE - INTERVAL '1 day', 'Eggs', 120),

    (CURRENT_DATE - INTERVAL '1 day', 'Whole wheat bread', 60)

) AS d(log_date, product_name, grams)

JOIN product p ON p.name = d.product_name

WHERE u.email = 'maria@calyx.local';



INSERT INTO daily_log (user_id, log_date, entry_type, product_id, meal_id, grams, servings, calories, proteins, fats, carbs)

SELECT u.id, d.log_date::date, 'MEAL', NULL, m.id, NULL, d.servings,

       CAST(ROUND(m.total_calories * d.servings) AS INTEGER),

       ROUND((m.total_proteins * d.servings)::numeric, 2),

       ROUND((m.total_fats * d.servings)::numeric, 2),

       ROUND((m.total_carbs * d.servings)::numeric, 2)

FROM users u

JOIN meal m ON m.user_id = u.id

JOIN (VALUES

    (CURRENT_DATE, 'Greek yogurt breakfast', 1.0),

    (CURRENT_DATE, 'Chicken rice bowl', 1.0),

    (CURRENT_DATE - INTERVAL '1 day', 'Salmon salad', 1.0)

) AS d(log_date, meal_name, servings)

    ON m.name = d.meal_name

WHERE u.email = 'maria@calyx.local';

