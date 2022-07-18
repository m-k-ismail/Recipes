package com.abn.recipe.service.validator;

import com.abn.recipe.domain.model.RecipeBo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
public class RecipeCreatorValidator {

    private static final List<String> MANDATORY_FIELDS =
            List.of("title", "numberOfServings", "instructions", "type", "ingredients[0].name", "ingredients[0].quantity");

    private final RecipeValidator recipeValidator;

    @Autowired
    public RecipeCreatorValidator(RecipeValidator recipeValidator) {
        this.recipeValidator = recipeValidator;
    }

    public void validate(RecipeBo recipeBo) {
        log.info("Create validator is triggered");
        recipeValidator.validateRequestBodyIsNotNull(recipeBo);
        recipeValidator.validateInputIdIsNull(recipeBo);
        recipeValidator.validateMandatoryFields(recipeBo, MANDATORY_FIELDS);
    }
}
