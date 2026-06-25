ALTER TABLE product ADD COLUMN IF NOT EXISTS category VARCHAR(50) NOT NULL DEFAULT 'other';

UPDATE product SET category = 'fruits' WHERE name IN ('Banana', 'Apple');
UPDATE product SET category = 'vegetables' WHERE name IN ('Broccoli', 'Spinach', 'Cherry tomatoes', 'Avocado');
UPDATE product SET category = 'protein' WHERE name IN ('Chicken breast', 'Salmon fillet', 'Eggs', 'Cottage cheese', 'Greek yogurt', 'Almonds');
UPDATE product SET category = 'grains' WHERE name IN ('Brown rice', 'Oatmeal', 'Whole wheat bread');
