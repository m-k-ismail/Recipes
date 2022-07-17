package com.abn.recipe.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public enum ErrorType {

    TECHNICAL_ERROR(11111),
    INVALID_BODY(11112, "Request body can not be null"),
    INPUT_ID_IS_NOT_NULL(11113, "Input should not have an ID"),
    AUTHENTICATION_FAILED(11114, "User is not authorized"),
    MISSING_MANDATORY_FIELD(11115);

    @NonNull
    private final Integer code;
    private String message;
}
