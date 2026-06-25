-- Align users table with application schema (safe to re-run)
ALTER TABLE users ADD COLUMN IF NOT EXISTS password VARCHAR(255) NOT NULL DEFAULT '';
ALTER TABLE users ADD COLUMN IF NOT EXISTS age INT NOT NULL DEFAULT 25;
ALTER TABLE users ADD COLUMN IF NOT EXISTS weight_kg DOUBLE PRECISION NOT NULL DEFAULT 70;
ALTER TABLE users ADD COLUMN IF NOT EXISTS height_cm INT NOT NULL DEFAULT 170;
ALTER TABLE users ADD COLUMN IF NOT EXISTS goal VARCHAR(50) NOT NULL DEFAULT 'maintain';

UPDATE users
SET password = COALESCE(NULLIF(password, ''), 'seed'),
    age = CASE WHEN age IS NULL OR age = 25 THEN 28 ELSE age END,
    weight_kg = CASE WHEN weight_kg = 70 THEN 65 ELSE weight_kg END,
    height_cm = CASE WHEN height_cm = 170 THEN 168 ELSE height_cm END,
    goal = CASE WHEN goal = 'maintain' THEN 'lose_weight' ELSE goal END
WHERE email = 'maria@calyx.local';
