package com.abn.recipe.service.validator;

import com.abn.recipe.domain.model.RecipeBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecipeUpdateValidator {
    private static final List<String> MANDATORY_FIELDS =
            List.of("id", "createdAt", "title", "numberOfServings", "instructions", "type", "ingredients[0].name", "ingredients[0].quantity");

    private final RecipeValidator recipeValidator;

    @Autowired
    public RecipeUpdateValidator(RecipeValidator recipeValidator) {
        this.recipeValidator = recipeValidator;
    }

    public void validate(Long id, RecipeBo recipeBo) {
        recipeValidator.validateRecipeId(id);
        recipeValidator.validateRequestBodyIsNotNull(recipeBo);
        recipeValidator.validateMandatoryFields(recipeBo, MANDATORY_FIELDS);
    }
}