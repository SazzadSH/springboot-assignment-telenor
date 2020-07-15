package com.telenor.assignment.helper;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse<T> {

    private String error;
    private HttpStatus status;
    private int code;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String stacktrace;

    private ApiErrorResponse() {
        timestamp = LocalDateTime.now();
    }

    public ApiErrorResponse(HttpStatus status, Throwable t) {
        this.error = t.getMessage();
        this.status = status;
        code = status.value();
        this.stacktrace = ExceptionUtils.getStackTrace(t);
    }

    public ApiErrorResponse(HttpStatus status, String error) {
        this.error = error;
        code = status.value();
        this.status = status;
    }
    public ApiErrorResponse(HttpStatus status) {
        this.status = status;
        code = status.value();
        this.error = "Unexpected error has occurred.";
    }
}
