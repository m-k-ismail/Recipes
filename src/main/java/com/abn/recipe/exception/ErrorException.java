package com.abn.recipe.exception;

import lombok.Getter;

@Getter
public class ErrorException extends RuntimeException {

    private final Integer code;

    public ErrorException(RecipeErrorType recipeErrorType) {
        super(recipeErrorType.getMessage());
        this.code = recipeErrorType.getCode();
    }

}
