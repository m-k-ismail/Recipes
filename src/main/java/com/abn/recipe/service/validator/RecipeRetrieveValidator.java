package com.abn.recipe.service.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecipeRetrieveValidator {

    private final RecipeValidator recipeValidator;

    @Autowired
    public RecipeRetrieveValidator(RecipeValidator recipeValidator){
        this.recipeValidator = recipeValidator;
    }

    public void validate(Long id){
        recipeValidator.validateRecipeId(id);
    }
}
