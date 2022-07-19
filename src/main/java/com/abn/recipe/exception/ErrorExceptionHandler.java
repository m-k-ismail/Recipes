package com.abn.recipe.exception;

import com.abn.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * A Rest Advice that do exception handling for the APIs
 *
 * @author mismail
 */
@RestControllerAdvice
public class ErrorExceptionHandler {

    /**
     * Handles {@link ErrorException} occurrences and creates a constructed error that can be
     * understandable for the API consumers
     *
     * @param e the {@link ErrorException} that occurred in a functional flow
     * @return a {@link ResponseEntity} with the error content using the {@link Error} model
     */
    @ExceptionHandler(value = {ErrorException.class})
    public ResponseEntity<Error> handleErrorException(ErrorException e){
        Error error = new Error();
        error.setCode(e.getCode());
        error.setMessage(e.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link RuntimeException} occurrences and creates a constructed error that can be
     * understandable for the API consumers. The errors that are handled by this method are unexpected technical errors
     *
     * @param e the {@link RuntimeException} that occurred in a functional flow
     * @return a {@link ResponseEntity} with the error content using the {@link Error} model
     */
    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<Error> handleRuntimeException(RuntimeException e){
        Error error = new Error();
        error.setCode(ErrorType.TECHNICAL_ERROR.getCode());
        error.setMessage(e.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
