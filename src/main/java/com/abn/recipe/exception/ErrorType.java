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
    INVALID_BODY(11112, "Request body can not be null");

    @NonNull
    private final Integer code;
    private String message;
}
