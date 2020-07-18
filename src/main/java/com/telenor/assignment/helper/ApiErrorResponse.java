package com.telenor.assignment.helper;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.telenor.assignment.util.Constants;
import lombok.Data;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static com.telenor.assignment.util.Constants.ERROR_RESPONSE_DATE_FORMAT;
import static com.telenor.assignment.util.Constants.UNKNOWN_ERROR_RESPONSE_MESSAGE;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse<T> {

    private String error;
    private HttpStatus status;
    private int code;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ERROR_RESPONSE_DATE_FORMAT)
    private LocalDateTime timestamp;
    private String stacktrace;

    private ApiErrorResponse() {
        timestamp = LocalDateTime.now();
    }

    public ApiErrorResponse(HttpStatus status, Throwable t) {
        super();
        this.error = t.getMessage();
        this.status = status;
        code = status.value();
        this.stacktrace = ExceptionUtils.getStackTrace(t);
    }

    public ApiErrorResponse(HttpStatus status, String error) {
        super();
        this.error = error;
        code = status.value();
        this.status = status;
    }
    public ApiErrorResponse(HttpStatus status) {
        super();
        this.status = status;
        code = status.value();
        this.error = UNKNOWN_ERROR_RESPONSE_MESSAGE;
    }
}
