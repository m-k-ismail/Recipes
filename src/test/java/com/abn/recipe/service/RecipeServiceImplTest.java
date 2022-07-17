package com.abn.recipe.service;

import com.abn.recipe.domain.entity.IngredientEntity;
import com.abn.recipe.domain.entity.RecipeEntity;
import com.abn.recipe.domain.mapper.RecipeEntityMapperImpl;
import com.abn.recipe.domain.model.IngredientBo;
import com.abn.recipe.domain.model.RecipeBo;
import com.abn.recipe.domain.model.TypeEnumBo;
import com.abn.recipe.repository.RecipeRepository;
import com.abn.recipe.service.validator.RecipeCreatorValidator;
import com.abn.recipe.service.validator.RecipeRetrieveValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceImplTest {

    private static final String RECIPE_CREATION_DATE = "2022-07-07";

    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Spy
    private RecipeEntityMapperImpl recipeEntityMapper;
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private RecipeCreatorValidator recipeCreatorValidator;
    @Mock
    private RecipeRetrieveValidator recipeRetrieveValidator;

    @Test
    public void should_create_recipe() throws ParseException {
        // given
        RecipeBo recipeBo = createRecipeBo(null);
        RecipeEntity recipeEntity = createRecipeEntity();
        when(recipeRepository.save(any(RecipeEntity.class))).thenReturn(recipeEntity);

        // when
        RecipeBo outputRecipeBo = recipeService.create(recipeBo);

        // then
        Assertions.assertNotNull(outputRecipeBo.getId());
        Assertions.assertNotNull(outputRecipeBo.getCreatedAt());
    }

    @Test
    public void should_retrieve_recipe_when_id_is_given() throws ParseException {
        // given
        Long recipeId = 1L;
        RecipeEntity recipeEntity = createRecipeEntity();
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipeEntity));

        // when
        RecipeBo outputRecipeBo = recipeService.searchById(recipeId);

        // then
        Assertions.assertEquals(recipeEntity.getId(), outputRecipeBo.getId());
    }


    private RecipeBo createRecipeBo(Long recipeId) {
        String title = "My Recipe";
        LocalDate createdAt = LocalDate.parse(RECIPE_CREATION_DATE);
        Integer numberOfServings = 1;
        String instructions = "Instructions";
        TypeEnumBo type = TypeEnumBo.VEGETARIAN;
        String ingredientName = "potato";
        Integer ingredientQuantity = 3;
        List<IngredientBo> ingredients = List.of(new IngredientBo(ingredientName, ingredientQuantity));

        return new RecipeBo(recipeId, title, createdAt, numberOfServings, instructions, type, ingredients);
    }

    private RecipeEntity createRecipeEntity() throws ParseException {
        Long id = 1L;
        String title = "My Recipe";
        Date createdAt = new SimpleDateFormat("yyyy-MM-dd").parse(RECIPE_CREATION_DATE);
        Integer numberOfServings = 1;
        String instructions = "Instructions";
        String type = TypeEnumBo.VEGETARIAN.name();
        String ingredientName = "potato";
        Integer ingredientQuantity = 3;
        List<IngredientEntity> ingredients = List.of(new IngredientEntity(null, ingredientName, ingredientQuantity, null));

        return new RecipeEntity(id, title, createdAt, numberOfServings, instructions, type, ingredients);

    }
}
