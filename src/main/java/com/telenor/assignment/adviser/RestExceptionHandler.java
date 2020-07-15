package com.telenor.assignment.adviser;

import com.telenor.assignment.expection.IllegalProductTypeException;
import com.telenor.assignment.helper.ApiErrorResponse;
import lombok.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({IllegalProductTypeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiErrorResponse<List<StackTraceElement>> processValidationError(Exception ex) {
        ApiErrorResponse<List<StackTraceElement>> response = new ApiErrorResponse<>(ex.getMessage());
        return response;
    }

}
