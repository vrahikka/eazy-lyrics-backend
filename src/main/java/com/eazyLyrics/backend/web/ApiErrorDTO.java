package com.eazyLyrics.backend.web;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ApiErrorDTO {

    private final String errorCode;
    private final List<FieldErrorDTO> validationErrors = new ArrayList<>();

    public ApiErrorDTO(String errorCode) {
        this.errorCode = errorCode;
    }

    public void addFieldError(String field, String errorCode) {
        FieldErrorDTO error = new FieldErrorDTO(field, errorCode);
        validationErrors.add(error);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public List<FieldErrorDTO> getValidationErrors() {
        return validationErrors;
    }
}