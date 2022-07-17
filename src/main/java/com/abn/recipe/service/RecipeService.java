package com.abn.recipe.service;

import com.abn.recipe.domain.model.RecipeBo;

public interface RecipeService {
    RecipeBo create(RecipeBo recipeBo);

    RecipeBo searchById(Long recipeId);

    void delete(Long recipeId);
}