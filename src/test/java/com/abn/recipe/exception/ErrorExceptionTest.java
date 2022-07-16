package com.abn.recipe.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ErrorExceptionTest {

    @Test
    public void should_return_correct_error_code_and_error_message_when_error_exception_is_initialized() {
        // given
        Integer expectedErrorCode = 11112;
        String expectedErrorMessage = "Request body can not be null";

        // when
        ErrorException errorException = new ErrorException(RecipeErrorType.INVALID_BODY);

        // then
        Assertions.assertEquals(expectedErrorCode, errorException.getCode());
        Assertions.assertEquals(expectedErrorMessage, errorException.getMessage());
    }
}
