package com.abn.recipe.service.validator;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class RecipeRetrieveValidator {

    private final RecipeValidator recipeValidator;

    @Autowired
    public RecipeRetrieveValidator(RecipeValidator recipeValidator){
        this.recipeValidator = recipeValidator;
    }

    public void validate(Long id){
        log.info("Retrieve validator is triggered");
        recipeValidator.validateRecipeId(id);
    }
}
