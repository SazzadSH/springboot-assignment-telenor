package com.telenor.assignment.adviser;

import com.telenor.assignment.expection.IllegalProductTypeException;
import com.telenor.assignment.helper.ApiErrorResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${include.trace.for.debug:#{false}}")
    private boolean includeTraceForDebug;

    @ExceptionHandler({IllegalProductTypeException.class})
    @ResponseStatus(code=HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiErrorResponse<List<StackTraceElement>> errorResponseForIllegalProductTypeException(Exception ex) {
        ApiErrorResponse<List<StackTraceElement>> response = includeTraceForDebug ?
                new ApiErrorResponse<>(HttpStatus.BAD_REQUEST,ex) :
                new ApiErrorResponse<>(HttpStatus.BAD_REQUEST,ex.getMessage()) ;
        return response;
    }

}
