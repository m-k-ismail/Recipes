package com.abn.recipe.service.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RecipeRetrieveValidatorTest {

    @InjectMocks
    private RecipeRetrieveValidator recipeRetrieveValidator;

    @Mock
    private RecipeValidator recipeValidator;

    @Test
    public void should_validate_recipe_in_order(){
        // given
        Long recipeId = 1L;

        // when
        recipeRetrieveValidator.validate(recipeId);

        // then
        InOrder inOrder = Mockito.inOrder(recipeValidator);
        inOrder.verify(recipeValidator).validateRecipeId(recipeId);
        inOrder.verifyNoMoreInteractions();
    }
}
