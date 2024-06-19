package com.eazyLyrics.backend.web;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApiErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiErrorDTO handleValidationError(MethodArgumentNotValidException ex) {
        var result = ex.getBindingResult();
        var fieldErrors = result.getFieldErrors();

        var apiError = new ApiErrorDTO("VALIDATION_FAILED");

        for (FieldError fieldError: fieldErrors) {
            var errorCodes = fieldError.getCodes();
            if (errorCodes != null && errorCodes.length > 0) {
                apiError.addFieldError(fieldError.getField(), errorCodes[errorCodes.length - 1]);
            }
        }

        return apiError;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiErrorDTO handleSQLError(DataIntegrityViolationException ex) {
        return new ApiErrorDTO("DATA_BASE_ERROR", ex.getCause().getMessage());
    }

    @ExceptionHandler(SongNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiErrorDTO handleNotFoundError(SongNotFoundException ex) {
        return new ApiErrorDTO("NOT_FOUND", ex.getMessage());
    }
}
