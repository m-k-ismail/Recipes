package com.abn.recipe.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class RecipeBoTest {
    @Test
    public void should_create_recipe_with_all_args() {
        // given
        Long id = null;
        String title = "My Recipe";
        LocalDate createdAt = LocalDate.now();
        Integer numberOfServings = 1;
        String instructions = "Instructions";
        TypeEnumBo type = TypeEnumBo.VEGETARIAN;
        String ingredientName = "potato";
        Integer ingredientQuantity = 3;
        List<IngredientBo> ingredients = List.of(new IngredientBo(ingredientName, ingredientQuantity));

        // when
        RecipeBo recipeBo = new RecipeBo(id, title, createdAt, numberOfServings, instructions, type, ingredients);

        // then
        Assertions.assertEquals(id, recipeBo.getId());
        Assertions.assertEquals(title, recipeBo.getTitle());
        Assertions.assertEquals(createdAt, recipeBo.getCreatedAt());
        Assertions.assertEquals(numberOfServings, recipeBo.getNumberOfServings());
        Assertions.assertEquals(instructions, recipeBo.getInstructions());
        Assertions.assertEquals(type, recipeBo.getType());
        Assertions.assertEquals(ingredients, recipeBo.getIngredients());
    }


    @Test
    public void should_create_recipe_with_no_args() {
        // given

        // when
        RecipeBo recipeBo = new RecipeBo();

        // then
        Assertions.assertNull(recipeBo.getId());
        Assertions.assertNull(recipeBo.getTitle());
        Assertions.assertNull(recipeBo.getCreatedAt());
        Assertions.assertNull(recipeBo.getNumberOfServings());
        Assertions.assertNull(recipeBo.getInstructions());
        Assertions.assertNull(recipeBo.getType());
        Assertions.assertNull(recipeBo.getIngredients());
    }
}
