package com.abn.recipe.service;

import com.abn.recipe.domain.model.RecipeBo;
import com.abn.recipe.repository.RecipeSearchParams;

import java.util.List;

public interface RecipeService {
    RecipeBo create(RecipeBo recipeBo);

    RecipeBo searchById(Long recipeId);

    List<RecipeBo> searchBySearchParams(RecipeSearchParams recipeSearchParams);

    void delete(Long recipeId);

    RecipeBo update(Long recipeId, RecipeBo recipeBo);
}
