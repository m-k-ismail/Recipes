package com.abn.recipe.service.validator;

import com.abn.recipe.domain.model.RecipeBo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class RecipeUpdateValidatorTest {

    @InjectMocks
    private RecipeUpdateValidator recipeUpdateValidator;

    @Mock
    private RecipeValidator recipeValidator;

    @Test
    public void should_validate_recipe_in_order(){
        // given
        Long recipeId = 1L;
        RecipeBo recipeBo = new RecipeBo();

        // when
        recipeUpdateValidator.validate(recipeId, recipeBo);

        // then
        InOrder inOrder = Mockito.inOrder(recipeValidator);
        inOrder.verify(recipeValidator).validateRecipeId(recipeId);
        inOrder.verify(recipeValidator).validateRequestBodyIsNotNull(recipeBo);
        inOrder.verify(recipeValidator).validateMandatoryFields(eq(recipeBo), any(List.class));
        inOrder.verifyNoMoreInteractions();
    }
}
