package com.abn.recipe.service;

import com.abn.recipe.domain.entity.IngredientEntity;
import com.abn.recipe.domain.entity.RecipeEntity;
import com.abn.recipe.domain.mapper.RecipeEntityMapperImpl;
import com.abn.recipe.domain.model.IngredientBo;
import com.abn.recipe.domain.model.RecipeBo;
import com.abn.recipe.domain.model.TypeEnumBo;
import com.abn.recipe.exception.ErrorException;
import com.abn.recipe.repository.RecipeRepository;
import com.abn.recipe.repository.RecipeSearchParams;
import com.abn.recipe.service.validator.RecipeCreatorValidator;
import com.abn.recipe.service.validator.RecipeDeleteValidator;
import com.abn.recipe.service.validator.RecipeRetrieveValidator;
import com.abn.recipe.service.validator.RecipeUpdateValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.abn.recipe.exception.ErrorType.RECIPE_IS_NOT_FOUND;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceImplTest {

    private static final String RECIPE_CREATION_DATE = "2022-07-07";
    private static final String RECIPE_TITLE = "My Recipe";
    private static final String RECIPE_NEW_TITLE = "My New Recipe";
    private static final TypeEnumBo RECIPE_TYPE = TypeEnumBo.VEGETARIAN;

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
    @Mock
    private RecipeDeleteValidator recipeDeleteValidator;
    @Mock
    private RecipeUpdateValidator recipeUpdateValidator;

    @Test
    public void should_create_recipe() throws ParseException {
        // given
        RecipeBo recipeBo = createRecipeBo(null, RECIPE_TITLE);
        RecipeEntity recipeEntity = createRecipeEntity(RECIPE_TITLE);
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
        RecipeEntity recipeEntity = createRecipeEntity(RECIPE_TITLE);
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipeEntity));

        // when
        RecipeBo outputRecipeBo = recipeService.searchById(recipeId);

        // then
        Assertions.assertEquals(recipeEntity.getId(), outputRecipeBo.getId());
    }

    @Test
    public void should_retrieve_recipe_when_search_param_is_given() throws ParseException {
        // given
        int pageLimit = 20;
        int pageOffset = 0;
        RecipeSearchParams recipeSearchParams = new RecipeSearchParams();
        recipeSearchParams.setType("VEGETARIAN");
        recipeSearchParams.setPageLimit(pageLimit);
        recipeSearchParams.setPageOffset(pageOffset);
        RecipeEntity recipeEntity = createRecipeEntity(RECIPE_TITLE);
        when(recipeRepository.findAll(any(Specification.class), eq(PageRequest.of(pageOffset, pageLimit))))
                .thenReturn(new PageImpl<>(List.of(recipeEntity)));

        // when
        List<RecipeBo> outputRecipeBos = recipeService.searchBySearchParams(recipeSearchParams);

        // then
        Assertions.assertEquals(RECIPE_TYPE, outputRecipeBos.get(0).getType());
    }

    @Test
    public void should_delete_recipe_when_id_is_given() {
        // given
        Long recipeId = 1L;

        // when
        recipeService.delete(recipeId);

        // then
        InOrder inOrder = Mockito.inOrder(recipeDeleteValidator, recipeRepository);
        inOrder.verify(recipeDeleteValidator).validate(recipeId);
        inOrder.verify(recipeRepository).deleteById(recipeId);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void should_update_recipe_when_correct_id_is_given() throws ParseException {
        // given
        Long recipeId = 1L;
        RecipeBo newRecipeBo = createRecipeBo(recipeId, RECIPE_NEW_TITLE);
        RecipeEntity existingRecipeEntity = createRecipeEntity(RECIPE_TITLE);
        RecipeEntity newRecipeEntity = createRecipeEntity(RECIPE_NEW_TITLE);
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(existingRecipeEntity));
        when(recipeRepository.save(any(RecipeEntity.class))).thenReturn(newRecipeEntity);

        // when
        RecipeBo outputRecipeBo = recipeService.update(recipeId, newRecipeBo);

        // then
        Assertions.assertEquals(recipeId, outputRecipeBo.getId());
        Assertions.assertEquals(RECIPE_NEW_TITLE, outputRecipeBo.getTitle());
    }

    @Test
    public void should_throw_error_exception_when_recipe_is_not_found() {
        // given
        Long recipeId = 1L;
        RecipeBo newRecipeBo = createRecipeBo(recipeId, RECIPE_NEW_TITLE);
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.empty());

        // when
        Exception exception = Assertions.assertThrows(ErrorException.class, () ->
                recipeService.update(recipeId, newRecipeBo));

        // then
        Assertions.assertEquals(RECIPE_IS_NOT_FOUND.getMessage(), exception.getMessage());
    }

    private RecipeBo createRecipeBo(Long recipeId, String title) {
        LocalDate createdAt = LocalDate.parse(RECIPE_CREATION_DATE);
        Integer numberOfServings = 1;
        String instructions = "Instructions";
        String ingredientName = "potato";
        Integer ingredientQuantity = 3;
        List<IngredientBo> ingredients = List.of(new IngredientBo(ingredientName, ingredientQuantity));

        return new RecipeBo(recipeId, title, createdAt, numberOfServings, instructions, RECIPE_TYPE, ingredients);
    }

    private RecipeEntity createRecipeEntity(String title) throws ParseException {
        Long id = 1L;
        Date createdAt = new SimpleDateFormat("yyyy-MM-dd").parse(RECIPE_CREATION_DATE);
        Integer numberOfServings = 1;
        String instructions = "Instructions";
        String type = RECIPE_TYPE.name();
        String ingredientName = "potato";
        Integer ingredientQuantity = 3;
        List<IngredientEntity> ingredients = List.of(new IngredientEntity(null, ingredientName, ingredientQuantity, null));

        return new RecipeEntity(id, title, createdAt, numberOfServings, instructions, instructions, type, ingredients);

    }
}
