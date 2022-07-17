package com.abn.recipe.service;

import com.abn.recipe.domain.entity.RecipeEntity;
import com.abn.recipe.domain.mapper.RecipeEntityMapper;
import com.abn.recipe.domain.model.RecipeBo;
import com.abn.recipe.repository.RecipeRepository;
import com.abn.recipe.service.validator.RecipeCreatorValidator;
import com.abn.recipe.service.validator.RecipeRetrieveValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeEntityMapper recipeEntityMapper;
    private final RecipeCreatorValidator recipeCreatorValidator;
    private final RecipeRetrieveValidator recipeRetrieveValidator;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeEntityMapper recipeEntityMapper,
                             RecipeCreatorValidator recipeCreatorValidator,
                             RecipeRetrieveValidator recipeRetrieveValidator) {
        this.recipeRepository = recipeRepository;
        this.recipeEntityMapper = recipeEntityMapper;
        this.recipeCreatorValidator = recipeCreatorValidator;
        this.recipeRetrieveValidator = recipeRetrieveValidator;
    }

    @Override
    public RecipeBo create(RecipeBo recipeBo) {
        recipeCreatorValidator.validate(recipeBo);

        recipeBo.setCreatedAt(LocalDate.now());

        RecipeEntity recipeEntity = recipeEntityMapper.mapToEntity(recipeBo);

        RecipeEntity savedRecipeEntity = recipeRepository.save(recipeEntity);

        return recipeEntityMapper.mapToBo(savedRecipeEntity);
    }

    @Override
    public RecipeBo searchById(Long recipeId) {
        recipeRetrieveValidator.validate(recipeId);

        Optional<RecipeEntity> recipeEntityOptional = recipeRepository.findById(recipeId);

        RecipeEntity recipeEntity = recipeEntityOptional.orElse(null);

        return recipeEntityMapper.mapToBo(recipeEntity);
    }
}
