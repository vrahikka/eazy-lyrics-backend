package com.eazyLyrics.backend.web;

import jakarta.annotation.Nullable;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ApiErrorDTO {

    private final String errorCode;

    private String errorMessage;
    private final List<FieldErrorDTO> validationErrors = new ArrayList<>();

    public ApiErrorDTO(String errorCode) {
        this.errorCode = errorCode;
    }

    public ApiErrorDTO(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public void addFieldError(String field, String errorCode) {
        FieldErrorDTO error = new FieldErrorDTO(field, errorCode);
        validationErrors.add(error);
    }

}