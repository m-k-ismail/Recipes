package com.abn.recipe.controller;

import com.abn.api.RecipeApi;
import com.abn.model.Recipe;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class RecipeApiImpl implements RecipeApi {

    @Override
    public ResponseEntity<Recipe> createRecipe(Recipe recipe) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Override
    public ResponseEntity<Void> deleteRecipe(String id) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Override
    public ResponseEntity<Recipe> getRecipe(String id) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Override
    public ResponseEntity<Recipe> getRecipes(Integer numberOfServings, String type, Set<String> includedIngredient,
                                             Set<String> excludedIngredient, String freeText, Integer pageLimit,
                                             Integer pageOffset) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Override
    public ResponseEntity<Recipe> updateRecipe(String id, Recipe recipe) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
