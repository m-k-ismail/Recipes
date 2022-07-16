package com.abn.recipe.controller;

import com.abn.api.RecipeApi;
import com.abn.model.Recipe;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("v1/api")
public class RecipeApiImpl implements RecipeApi {

    @Override
    public ResponseEntity<Recipe> createRecipe(Recipe recipe) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Override
    public ResponseEntity<Void> deleteRecipe(Long id) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Override
    public ResponseEntity<Recipe> getRecipe(Long id) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Override
    public ResponseEntity<Recipe> getRecipes(Integer numberOfServings, String type, Set<String> includedIngredient,
                                             Set<String> excludedIngredient, String freeText, Integer pageLimit,
                                             Integer pageOffset) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Override
    public ResponseEntity<Recipe> updateRecipe(Long id, Recipe recipe) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
