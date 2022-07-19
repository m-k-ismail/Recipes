SELECT 'CREATE DATABASE recipedb'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'recipedb')\gexec

CREATE TABLE IF NOT EXISTS RECIPE
(
    id                 INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    title              VARCHAR(150)                                 NOT NULL,
    created_at         DATE                                         NOT NULL,
    number_of_servings SMALLINT                                     NOT NULL,
    instructions       TEXT                                         NOT NULL,
    instructions_tsv   TSVECTOR                                     NOT NULL,
    type               VARCHAR(40)                                  NOT NULL
);


CREATE TABLE IF NOT EXISTS INGREDIENT
(
    id        INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    name      VARCHAR(100)                                 NOT NULL,
    quantity  SMALLINT                                     NOT NULL,
    recipe_id INT,
    CONSTRAINT fk_recipe FOREIGN KEY (recipe_id) REFERENCES RECIPE (id)
);

CREATE INDEX IF NOT EXISTS idx_recipe_number_of_servings
    ON recipe(number_of_servings);

CREATE INDEX IF NOT EXISTS idx_recipe_type
    ON recipe(type);

CREATE INDEX IF NOT EXISTS idx_ingredient_name
    ON ingredient(name);

CREATE INDEX IF NOT EXISTS idx_instructions_tsv
    ON recipe USING GIN (instructions_tsv);