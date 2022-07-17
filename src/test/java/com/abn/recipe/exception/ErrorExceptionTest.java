package com.abn.recipe.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ErrorExceptionTest {

    @Test
    public void should_return_correct_error_code_and_error_message_when_error_exception_is_initialized_by_error_type() {
        // given
        Integer expectedErrorCode = 11112;
        String expectedErrorMessage = "Request body can not be null";

        // when
        ErrorException errorException = new ErrorException(ErrorType.INVALID_BODY);

        // then
        Assertions.assertEquals(expectedErrorCode, errorException.getCode());
        Assertions.assertEquals(expectedErrorMessage, errorException.getMessage());
    }


    @Test
    public void should_return_correct_error_code_and_error_message_when_error_exception_is_initialized_by_message_and_code() {
        // given
        Integer expectedErrorCode = 11112;
        String expectedErrorMessage = "Request body can not be null";

        // when
        ErrorException errorException = new ErrorException(expectedErrorCode, expectedErrorMessage);

        // then
        Assertions.assertEquals(expectedErrorCode, errorException.getCode());
        Assertions.assertEquals(expectedErrorMessage, errorException.getMessage());
    }
}
