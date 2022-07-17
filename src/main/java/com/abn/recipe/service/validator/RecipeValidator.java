package com.abn.recipe.service.validator;

import com.abn.recipe.domain.model.RecipeBo;
import com.abn.recipe.exception.ErrorException;
import com.abn.recipe.exception.ErrorType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.List;
import java.util.regex.Pattern;

@Component
public class RecipeValidator {

    private static final Pattern ID_PATTERN = Pattern.compile("^[0-9]*$");

    public void validateRequestBodyIsNotNull(RecipeBo recipeBo) {
        if (recipeBo == null) {
            throw new ErrorException(ErrorType.INVALID_BODY);
        }
    }

    public void validateInputIdIsNull(RecipeBo recipeBo) {
        if (recipeBo.getId() != null) {
            throw new ErrorException(ErrorType.INPUT_ID_IS_NOT_NULL);
        }
    }

    public void validateMandatoryFields(RecipeBo recipeBo, List<String> mandatoryFields) {
        BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(recipeBo, recipeBo.getClass().getSimpleName());

        for (String mandatoryField : mandatoryFields) {
            Object fieldValue = bindingResult.getFieldValue(mandatoryField);
            if (fieldValue == null) {
                throw new ErrorException(ErrorType.MISSING_MANDATORY_FIELD.getCode(), "Missing mandatory field, field: " + mandatoryField);
            }
        }
    }

    public void validateRecipeId(Long id) {
        if (id == null || !ID_PATTERN.matcher(String.valueOf(id)).matches()) {
            throw new ErrorException(ErrorType.RECIPE_ID_IS_INVALID);
        }
    }
}
