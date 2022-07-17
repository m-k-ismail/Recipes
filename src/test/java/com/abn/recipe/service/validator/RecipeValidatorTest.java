package com.abn.recipe.service.validator;

import com.abn.recipe.domain.model.RecipeBo;
import com.abn.recipe.exception.ErrorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RecipeValidatorTest {

    @InjectMocks
    private RecipeValidator recipeValidator;

    @Test
    public void should_throw_error_exception_when_body_is_null() {
        // given
        RecipeBo recipeBo = null;

        // when
        Exception exception = Assertions.assertThrows(ErrorException.class, () ->
                recipeValidator.validateRequestBodyIsNotNull(recipeBo));

        // then
        Assertions.assertEquals("Request body can not be null", exception.getMessage());
    }

    @Test
    public void should_throw_error_exception_when_input_id_is_null() {
        // given
        RecipeBo recipeBo = new RecipeBo();
        recipeBo.setId(1L);

        // when
        Exception exception = Assertions.assertThrows(ErrorException.class, () ->
                recipeValidator.validateInputIdIsNull(recipeBo));

        // then
        Assertions.assertEquals("Input should not have an ID", exception.getMessage());
    }

    @Test
    public void should_throw_error_exception_when_mandatory_field_is_missing() {
        // given
        RecipeBo recipeBo = new RecipeBo();

        // when
        Exception exception = Assertions.assertThrows(ErrorException.class, () ->
                recipeValidator.validateMandatoryFields(recipeBo, List.of("title")));

        // then
        Assertions.assertEquals("Missing mandatory field, field: title", exception.getMessage());
    }
}
