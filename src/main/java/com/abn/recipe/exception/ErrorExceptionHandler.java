package com.abn.recipe.exception;

import com.abn.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorExceptionHandler {

    @ExceptionHandler(value = {ErrorException.class})
    public ResponseEntity<Error> handleErrorException(ErrorException e){
        Error error = new Error();
        error.setCode(e.getCode());
        error.setMessage(e.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<Error> handleRuntimeException(RuntimeException e){
        Error error = new Error();
        error.setCode(RecipeErrorType.TECHNICAL_ERROR.getCode());
        error.setMessage(e.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
