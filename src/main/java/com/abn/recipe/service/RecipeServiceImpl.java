package com.abn.recipe.service;

import com.abn.recipe.domain.entity.RecipeEntity;
import com.abn.recipe.domain.mapper.RecipeEntityMapper;
import com.abn.recipe.domain.model.RecipeBo;
import com.abn.recipe.repository.RecipeRepository;
import com.abn.recipe.service.validator.RecipeCreatorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeEntityMapper recipeEntityMapper;
    private final RecipeCreatorValidator recipeCreatorValidator;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeEntityMapper recipeEntityMapper,
                             RecipeCreatorValidator recipeCreatorValidator) {
        this.recipeRepository = recipeRepository;
        this.recipeEntityMapper = recipeEntityMapper;
        this.recipeCreatorValidator = recipeCreatorValidator;
    }

    @Override
    public RecipeBo create(RecipeBo recipeBo) {
        recipeCreatorValidator.validate(recipeBo);

        recipeBo.setCreatedAt(LocalDate.now());

        RecipeEntity recipeEntity = recipeEntityMapper.mapToEntity(recipeBo);

        RecipeEntity savedRecipeEntity = recipeRepository.save(recipeEntity);

        return recipeEntityMapper.mapToBo(savedRecipeEntity);
    }
}
