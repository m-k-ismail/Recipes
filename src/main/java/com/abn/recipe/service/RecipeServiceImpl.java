package com.abn.recipe.service;

import com.abn.recipe.domain.entity.IngredientEntity;
import com.abn.recipe.domain.entity.RecipeEntity;
import com.abn.recipe.domain.mapper.RecipeEntityMapper;
import com.abn.recipe.domain.model.RecipeBo;
import com.abn.recipe.exception.ErrorException;
import com.abn.recipe.exception.ErrorType;
import com.abn.recipe.repository.RecipeRepository;
import com.abn.recipe.repository.RecipeSearchParams;
import com.abn.recipe.repository.RecipeSpecification;
import com.abn.recipe.service.validator.RecipeCreatorValidator;
import com.abn.recipe.service.validator.RecipeDeleteValidator;
import com.abn.recipe.service.validator.RecipeRetrieveValidator;
import com.abn.recipe.service.validator.RecipeUpdateValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
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
        log.info("create service has been triggered.");

        recipeCreatorValidator.validate(recipeBo);

        recipeBo.setCreatedAt(LocalDate.now());

        RecipeEntity recipeEntity = recipeEntityMapper.mapToEntity(recipeBo);

        RecipeEntity savedRecipeEntity = recipeRepository.save(recipeEntity);

        return recipeEntityMapper.mapToBo(savedRecipeEntity);
    }

    @Override
    public RecipeBo searchById(Long recipeId) {
        log.info("searchById service has been triggered.");

        recipeRetrieveValidator.validate(recipeId);

        Optional<RecipeEntity> recipeEntityOptional = recipeRepository.findById(recipeId);

        RecipeEntity recipeEntity = recipeEntityOptional.orElse(null);

        return recipeEntityMapper.mapToBo(recipeEntity);
    }

    @Override
    public List<RecipeBo> searchBySearchParams(RecipeSearchParams recipeSearchParams) {
        Specification<RecipeEntity> recipeEntitySpecification = Specification
                .where(RecipeSpecification.equalType(recipeSearchParams.getType())
                        .and(RecipeSpecification.equalNumberOfServings(recipeSearchParams.getNumberOfServings()))
                        .and(RecipeSpecification.includeIngredients(recipeSearchParams.getIncludedIngredient()))
                        .and(RecipeSpecification.excludeIngredients(recipeSearchParams.getExcludedIngredient()))
                        .and(RecipeSpecification.ftsFreeText(recipeSearchParams.getFreeText())));
        log.info("searchBySearchParams service has been triggered.");

        int maxPageLimit = 500;
        if(recipeSearchParams.getPageLimit() > maxPageLimit){
            recipeSearchParams.setPageLimit(maxPageLimit);
        }

        Page<RecipeEntity> recipeEntityPage = recipeRepository.findAll(recipeEntitySpecification,
                        PageRequest.of(recipeSearchParams.getPageOffset(), recipeSearchParams.getPageLimit()));
        List<RecipeEntity> recipeEntities = recipeEntityPage.getContent();

        return recipeEntityMapper.mapToBoList(recipeEntities);
    }

    @Override
    public void delete(Long recipeId) {
        log.info("delete service has been triggered.");

        recipeDeleteValidator.validate(recipeId);

        try {
            recipeRepository.deleteById(recipeId);
        } catch (EmptyResultDataAccessException exception) {
            throw new ErrorException(ErrorType.RECIPE_IS_NOT_FOUND);
        }
    }

    @Override
    public RecipeBo update(Long recipeId, RecipeBo recipeBo) {
        log.info("update service has been triggered.");

        recipeUpdateValidator.validate(recipeId, recipeBo);

        Optional<RecipeEntity> recipeEntityOptional = recipeRepository.findById(recipeId);
        RecipeEntity existingRecipeEntity = recipeEntityOptional.orElseThrow(() ->
                new ErrorException(ErrorType.RECIPE_IS_NOT_FOUND));

        RecipeEntity recipeEntity = recipeEntityMapper.mapToEntity(recipeBo);

        List<IngredientEntity> ingredients = recipeEntity.getIngredients();
        for (int index = 0; index < ingredients.size(); index++) {
            ingredients.set(index, existingRecipeEntity.getIngredients().get(index));
        }

        RecipeEntity updatedRecipeEntity = recipeRepository.save(recipeEntity);

        return recipeEntityMapper.mapToBo(updatedRecipeEntity);
    }
}
