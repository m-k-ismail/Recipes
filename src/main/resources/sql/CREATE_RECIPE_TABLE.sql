CREATE TABLE RECIPE
(
    id                 INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    title              VARCHAR(150)                                 NOT NULL,
    created_at         DATE                                         NOT NULL,
    number_of_servings SMALLINT                                     NOT NULL,
    instructions       TEXT                                         NOT NULL,
    type               VARCHAR(40)                                  NOT NULL
);


CREATE TABLE INGREDIENT
(
    id        INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    name      VARCHAR(100)                                 NOT NULL,
    quantity  SMALLINT                                     NOT NULL,
    recipe_id INT,
    CONSTRAINT fk_recipe FOREIGN KEY (recipe_id) REFERENCES RECIPE (id)
)