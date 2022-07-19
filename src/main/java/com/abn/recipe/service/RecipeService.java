package com.abn.recipe.service;

import com.abn.recipe.domain.model.RecipeBo;
import com.abn.recipe.repository.RecipeSearchParams;

import java.util.List;

/**
 * Service layer of Recipe APIs
 *
 * @author mismail
 */
public interface RecipeService {

    /**
     * Executes the creation flow of a recipe
     *
     * @param recipeBo the incoming recipe
     * @return output of the creation flow constructed as {@link RecipeBo}
     */
    RecipeBo create(RecipeBo recipeBo);

    /**
     * Executes the search by id flow of a recipe
     *
     * @param recipeId the recipe id
     * @return the recipe that was found by id constructed as {@link RecipeBo}
     */
    RecipeBo searchById(Long recipeId);

    /**
     * Executes the search by search parameters flow of a recipe
     *
     * @param recipeSearchParams the search parameters that are required to find the needed recipes
     * @return a list of recipes that were found according to the search parameters
     */
    List<RecipeBo> searchBySearchParams(RecipeSearchParams recipeSearchParams);

    /**
     * Executes the deletion flow of a recipe
     *
     * @param recipeId recipe id
     */
    void delete(Long recipeId);

    /**
     * Executes the update flow of a recipe
     *
     * @param recipeBo the incoming recipe
     * @param recipeId the id of the recipe that should be updated
     * @return output of the update flow constructed as {@link RecipeBo}
     */
    RecipeBo update(Long recipeId, RecipeBo recipeBo);
}
