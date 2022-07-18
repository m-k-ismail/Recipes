package com.abn.recipe.service.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecipeDeleteValidator {

    private static final Logger LOGGER = LogManager.getLogger(RecipeDeleteValidator.class);
    private final RecipeValidator recipeValidator;

    @Autowired
    public RecipeDeleteValidator(RecipeValidator recipeValidator){
        this.recipeValidator = recipeValidator;
    }

    public void validate(Long id){
        LOGGER.info("Delete validator is triggered");
        recipeValidator.validateRecipeId(id);
    }
}
