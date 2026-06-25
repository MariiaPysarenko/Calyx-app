CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    weight_kg DOUBLE PRECISION NOT NULL,
    height_cm INT NOT NULL,
    goal VARCHAR(50) NOT NULL
);

CREATE TABLE product (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(50) NOT NULL DEFAULT 'other',
    calories_per_100g INT NOT NULL,
    proteins_per_100g DOUBLE PRECISION NOT NULL,
    fats_per_100g DOUBLE PRECISION NOT NULL,
    carbs_per_100g DOUBLE PRECISION NOT NULL
);

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

CREATE TABLE bmi (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    weight_kg DOUBLE PRECISION NOT NULL,
    height_cm INT NOT NULL,
    bmi_value DOUBLE PRECISION NOT NULL,
    category VARCHAR(50) NOT NULL,
    calculated_at TIMESTAMP NOT NULL
);

CREATE TABLE daily_norm (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    daily_calories INT NOT NULL,
    proteins_norm DOUBLE PRECISION NOT NULL,
    fats_norm DOUBLE PRECISION NOT NULL,
    carbs_norm DOUBLE PRECISION NOT NULL
);
