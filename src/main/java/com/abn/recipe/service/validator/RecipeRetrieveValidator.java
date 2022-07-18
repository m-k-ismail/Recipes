package com.abn.recipe.service.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecipeRetrieveValidator {

    private static final Logger LOGGER = LogManager.getLogger(RecipeRetrieveValidator.class);
    private final RecipeValidator recipeValidator;

    @Autowired
    public RecipeRetrieveValidator(RecipeValidator recipeValidator){
        this.recipeValidator = recipeValidator;
    }

    public void validate(Long id){
        LOGGER.info("Retrieve validator is triggered");
        recipeValidator.validateRecipeId(id);
    }
}
