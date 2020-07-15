package com.telenor.assignment.helper;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse<T> {

    private String error;

    public ApiErrorResponse(String message) {
        error = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
