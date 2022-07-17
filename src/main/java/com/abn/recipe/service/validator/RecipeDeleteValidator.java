package com.abn.recipe.service.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecipeDeleteValidator {

    private final RecipeValidator recipeValidator;

    @Autowired
    public RecipeDeleteValidator(RecipeValidator recipeValidator){
        this.recipeValidator = recipeValidator;
    }

    public void validate(Long id){
        recipeValidator.validateRecipeId(id);
    }
}
