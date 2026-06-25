-- Migrate from time-based meals to recipe meals + daily log
DROP TABLE IF EXISTS meal_entry;
DROP TABLE IF EXISTS daily_log;
DROP TABLE IF EXISTS meal_ingredient;
DROP TABLE IF EXISTS meal;

CREATE TABLE meal (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    name VARCHAR(255) NOT NULL,
    total_calories INT NOT NULL DEFAULT 0,
    total_proteins DOUBLE PRECISION NOT NULL DEFAULT 0,
    total_fats DOUBLE PRECISION NOT NULL DEFAULT 0,
    total_carbs DOUBLE PRECISION NOT NULL DEFAULT 0
);

CREATE TABLE meal_ingredient (
    id BIGSERIAL PRIMARY KEY,
    meal_id BIGINT NOT NULL REFERENCES meal(id) ON DELETE CASCADE,
    product_id BIGINT NOT NULL REFERENCES product(id),
    grams DOUBLE PRECISION NOT NULL,
    calories INT NOT NULL,
    proteins DOUBLE PRECISION NOT NULL,
    fats DOUBLE PRECISION NOT NULL,
    carbs DOUBLE PRECISION NOT NULL
);

CREATE TABLE daily_log (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    log_date DATE NOT NULL,
    entry_type VARCHAR(20) NOT NULL,
    product_id BIGINT REFERENCES product(id),
    meal_id BIGINT REFERENCES meal(id),
    grams DOUBLE PRECISION,
    servings DOUBLE PRECISION,
    calories INT NOT NULL,
    proteins DOUBLE PRECISION NOT NULL,
    fats DOUBLE PRECISION NOT NULL,
    carbs DOUBLE PRECISION NOT NULL
);
