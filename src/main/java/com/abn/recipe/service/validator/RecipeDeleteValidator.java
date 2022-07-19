package com.abn.recipe.service.validator;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class RecipeDeleteValidator {

    private final RecipeValidator recipeValidator;

    @Autowired
    public RecipeDeleteValidator(RecipeValidator recipeValidator){
        this.recipeValidator = recipeValidator;
    }

    /**
     * Executes the necessary validations for the recipe delete flow
     *
     * @param id recipe id
     */
    public void validate(Long id){
        log.info("Delete validator is triggered");
        recipeValidator.validateRecipeId(id);
    }
}
