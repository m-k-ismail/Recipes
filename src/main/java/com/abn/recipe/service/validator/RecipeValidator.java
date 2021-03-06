package com.abn.recipe.service.validator;

import com.abn.recipe.domain.model.RecipeBo;
import com.abn.recipe.exception.ErrorException;
import com.abn.recipe.exception.ErrorType;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.List;
import java.util.regex.Pattern;

@Component
@Log4j2
public class RecipeValidator {

    private static final Pattern ID_PATTERN = Pattern.compile("^[0-9]*$");

    /**
     * Validates if the recipe is null
     *
     * @param recipeBo the incoming recipe
     * @throws ErrorException if the recipe is null
     */
    public void validateRequestBodyIsNotNull(RecipeBo recipeBo) {
        if (recipeBo == null) {
            log.info("recipeBo is null");
            throw new ErrorException(ErrorType.INVALID_BODY);
        }
    }

    /**
     * Validates if the recipe id is null or not
     *
     * @param recipeBo the incoming recipe
     * @throws ErrorException if the recipe id is not null
     */
    public void validateInputIdIsNull(RecipeBo recipeBo) {
        if (recipeBo.getId() != null) {
            log.info("recipeBo contains an id, id: {}", recipeBo.getId());
            throw new ErrorException(ErrorType.INPUT_ID_IS_NOT_NULL);
        }
    }

    /**
     * Validates if the recipe has any missing mandatory fields
     *
     * @param recipeBo the incoming recipe
     * @param mandatoryFields the list of fields paths that should be mandatory
     * @throws ErrorException if the recipe has a missing mandatory field
     */
    public void validateMandatoryFields(RecipeBo recipeBo, List<String> mandatoryFields) {
        log.info("mandatory field list: {}", mandatoryFields.toString());

        BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(recipeBo, recipeBo.getClass().getSimpleName());

        for (String mandatoryField : mandatoryFields) {
            Object fieldValue = bindingResult.getFieldValue(mandatoryField);
            if (fieldValue == null) {
                throw new ErrorException(ErrorType.MISSING_MANDATORY_FIELD.getCode(), "Missing mandatory field, field: " + mandatoryField);
            }
        }
    }

    /**
     * Validates if id is in the expected pattern or not
     *
     * @param id recipe id
     * @throws ErrorException if the id is null or wrongly formatted
     */
    public void validateRecipeId(Long id) {
        if (id == null || !ID_PATTERN.matcher(String.valueOf(id)).matches()) {
            log.info("recipe id validation failed, id: {}", id);
            throw new ErrorException(ErrorType.RECIPE_ID_IS_INVALID);
        }
    }
}
