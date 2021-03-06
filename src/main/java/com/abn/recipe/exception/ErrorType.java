package com.abn.recipe.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Enums for all the errors that are used for the application
 *
 * @author Mohammad Ismail
 */
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public enum ErrorType {

    TECHNICAL_ERROR(11111),
    INVALID_BODY(11112, "Request body can not be null"),
    INPUT_ID_IS_NOT_NULL(11113, "Input should not have an ID"),
    AUTHENTICATION_FAILED(11114, "User is not authorized"),
    MISSING_MANDATORY_FIELD(11115),
    RECIPE_ID_IS_INVALID(11116, "Invalid Recipe ID"),
    RECIPE_IS_NOT_FOUND(11117, "Recipe is not found");

    @NonNull
    private final Integer code;
    private String message;
}
