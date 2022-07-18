package com.abn.recipe.service.validator;

import com.abn.recipe.domain.model.RecipeBo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecipeCreatorValidator {

    private static final Logger LOGGER = LogManager.getLogger(RecipeCreatorValidator.class);
    private static final List<String> MANDATORY_FIELDS =
            List.of("title", "numberOfServings", "instructions", "type", "ingredients[0].name", "ingredients[0].quantity");

    private final RecipeValidator recipeValidator;

    @Autowired
    public RecipeCreatorValidator(RecipeValidator recipeValidator) {
        this.recipeValidator = recipeValidator;
    }

    public void validate(RecipeBo recipeBo) {
        LOGGER.info("Delete validator is triggered");
        recipeValidator.validateRequestBodyIsNotNull(recipeBo);
        recipeValidator.validateInputIdIsNull(recipeBo);
        recipeValidator.validateMandatoryFields(recipeBo, MANDATORY_FIELDS);
    }
}
