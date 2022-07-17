package com.abn.recipe.service;

import com.abn.recipe.domain.entity.IngredientEntity;
import com.abn.recipe.domain.entity.RecipeEntity;
import com.abn.recipe.domain.mapper.RecipeEntityMapper;
import com.abn.recipe.domain.model.RecipeBo;
import com.abn.recipe.exception.ErrorException;
import com.abn.recipe.exception.ErrorType;
import com.abn.recipe.repository.RecipeRepository;
import com.abn.recipe.service.validator.RecipeCreatorValidator;
import com.abn.recipe.service.validator.RecipeDeleteValidator;
import com.abn.recipe.service.validator.RecipeRetrieveValidator;
import com.abn.recipe.service.validator.RecipeUpdateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeEntityMapper recipeEntityMapper;
    private final RecipeCreatorValidator recipeCreatorValidator;
    private final RecipeRetrieveValidator recipeRetrieveValidator;
    private final RecipeDeleteValidator recipeDeleteValidator;
    private final RecipeUpdateValidator recipeUpdateValidator;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeEntityMapper recipeEntityMapper,
                             RecipeCreatorValidator recipeCreatorValidator,
                             RecipeRetrieveValidator recipeRetrieveValidator,
                             RecipeDeleteValidator recipeDeleteValidator,
                             RecipeUpdateValidator recipeUpdateValidator) {
        this.recipeRepository = recipeRepository;
        this.recipeEntityMapper = recipeEntityMapper;
        this.recipeCreatorValidator = recipeCreatorValidator;
        this.recipeRetrieveValidator = recipeRetrieveValidator;
        this.recipeDeleteValidator = recipeDeleteValidator;
        this.recipeUpdateValidator = recipeUpdateValidator;
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

    @Override
    public void delete(Long recipeId) {
        recipeDeleteValidator.validate(recipeId);

        try {
            recipeRepository.deleteById(recipeId);
        } catch (EmptyResultDataAccessException exception) {
            throw new ErrorException(ErrorType.RECIPE_IS_NOT_FOUND);
        }
    }

    @Override
    public RecipeBo update(Long recipeId, RecipeBo recipeBo) {
        recipeUpdateValidator.validate(recipeId, recipeBo);

        Optional<RecipeEntity> recipeEntityOptional = recipeRepository.findById(recipeId);
        RecipeEntity existingRecipeEntity = recipeEntityOptional.orElseThrow(() ->
                new ErrorException(ErrorType.RECIPE_IS_NOT_FOUND));

        RecipeEntity recipeEntity = recipeEntityMapper.mapToEntity(recipeBo);

        List<IngredientEntity> ingredients = recipeEntity.getIngredients();
        for(int index = 0; index< ingredients.size(); index++){
            ingredients.set(index, existingRecipeEntity.getIngredients().get(index));
        }

        RecipeEntity updatedRecipeEntity = recipeRepository.save(recipeEntity);

        return recipeEntityMapper.mapToBo(updatedRecipeEntity);
    }
}
