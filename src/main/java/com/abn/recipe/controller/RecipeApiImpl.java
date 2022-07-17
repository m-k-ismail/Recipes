package com.abn.recipe.controller;

import com.abn.api.RecipeApi;
import com.abn.model.Recipe;
import com.abn.model.RecipeREQ;
import com.abn.model.RecipeRES;
import com.abn.model.RecipesRES;
import com.abn.recipe.domain.mapper.RecipeBoMapper;
import com.abn.recipe.domain.model.RecipeBo;
import com.abn.recipe.exception.ErrorException;
import com.abn.recipe.exception.ErrorType;
import com.abn.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/v1")
public class RecipeApiImpl implements RecipeApi {

    private final RecipeService recipeService;
    private final RecipeBoMapper recipeBoMapper;

    @Autowired
    public RecipeApiImpl(RecipeService recipeService, RecipeBoMapper recipeBoMapper) {
        this.recipeService = recipeService;
        this.recipeBoMapper = recipeBoMapper;
    }

    @Override
    public ResponseEntity<RecipeRES> createRecipe(RecipeREQ recipe) {
        if (recipe == null) {
            throw new ErrorException(ErrorType.INVALID_BODY);
        }

        RecipeBo inputRecipeBo = recipeBoMapper.mapToBo(recipe.getData());

        RecipeBo outputRecipeBo = recipeService.create(inputRecipeBo);

        Recipe outputRecipe = recipeBoMapper.mapToModel(outputRecipeBo);

        return new ResponseEntity<>(new RecipeRES().data(outputRecipe), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteRecipe(Long id) {
        recipeService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RecipeRES> getRecipe(Long id) {
        RecipeBo recipeBo = recipeService.searchById(id);

        Recipe recipe = recipeBoMapper.mapToModel(recipeBo);

        RecipeRES recipeRES = new RecipeRES().data(recipe);

        HttpStatus httpStatus = HttpStatus.OK;
        if (recipeRES.getData() == null) {
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(recipeRES, httpStatus);
    }

    @Override
    public ResponseEntity<RecipesRES> getRecipes(Integer numberOfServings, String type, Set<String> includedIngredient,
                                                 Set<String> excludedIngredient, String freeText, Integer pageLimit,
                                                 Integer pageOffset) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Override
    public ResponseEntity<RecipeRES> updateRecipe(Long id, RecipeREQ recipe) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
