package com.abn.recipe.exception;

import com.abn.model.Error;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class ErrorExceptionHandlerTest {

    @InjectMocks
    private ErrorExceptionHandler errorExceptionHandler;

    @Test
    public void should_return_bad_request_when_error_exception_is_handled() {
        // given
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;
        ErrorType errorType = ErrorType.INVALID_BODY;
        ErrorException errorException = new ErrorException(errorType);
        Error error = new Error();
        error.setCode(errorType.getCode());
        error.setMessage(errorType.getMessage());

        // When
        ResponseEntity<Error> errorResponseEntity = errorExceptionHandler.handleErrorException(errorException);

        // then
        Assertions.assertEquals(expectedHttpStatus, errorResponseEntity.getStatusCode());
        Assertions.assertEquals(error, errorResponseEntity.getBody());
    }

    @Test
    public void should_return_internal_server_error_when_runtime_exception_is_handled() {
        // given
        HttpStatus expectedHttpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorType errorType = ErrorType.TECHNICAL_ERROR;
        String exceptionMessage = "Test Runtime Exception";
        RuntimeException runtimeException = new RuntimeException(exceptionMessage);

        // When
        ResponseEntity<Error> errorResponseEntity = errorExceptionHandler.handleRuntimeException(runtimeException);

        // then
        Assertions.assertEquals(expectedHttpStatus, errorResponseEntity.getStatusCode());
        Assertions.assertEquals(exceptionMessage, errorResponseEntity.getBody().getMessage());
        Assertions.assertEquals(errorType.getCode(), errorResponseEntity.getBody().getCode());
    }
}
