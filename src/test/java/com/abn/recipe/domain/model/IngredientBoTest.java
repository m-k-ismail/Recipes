package com.abn.recipe.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IngredientBoTest {

    @Test
    public void should_create_recipe_with_all_args(){
        // given
        String name = "potato";
        Integer quantity = 3;

        // when
        IngredientBo ingredientBo = new IngredientBo(name, quantity);

        // then
        Assertions.assertEquals(name, ingredientBo.getName());
        Assertions.assertEquals(quantity, ingredientBo.getQuantity());
    }


    @Test
    public void should_create_recipe_with_no_args(){
        // given

        // when
        IngredientBo ingredientBo = new IngredientBo();

        // then
        Assertions.assertNull(ingredientBo.getName());
        Assertions.assertNull(ingredientBo.getQuantity());
    }
}
