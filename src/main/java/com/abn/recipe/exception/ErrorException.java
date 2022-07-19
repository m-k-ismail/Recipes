package com.abn.recipe.exception;

import lombok.Getter;

/**
 * Exception used to stop the process
 *
 * @author Mohammad Ismail
 */
@Getter
public class ErrorException extends RuntimeException {

    private final Integer code;

    public ErrorException(ErrorType errorType) {
        super(errorType.getMessage());
        this.code = errorType.getCode();
    }

    public ErrorException(Integer code, String message) {
        super(message);
        this.code = code;
    }

}
