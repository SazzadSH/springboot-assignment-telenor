package com.telenor.assignment.handler;

import com.telenor.assignment.expection.IllegalProductTypeException;
import com.telenor.assignment.expection.IllegalPropertyTypeException;
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

import java.io.UnsupportedEncodingException;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${include.trace.for.debug:#{false}}")
    private boolean includeTraceForDebug;

    @ExceptionHandler({IllegalArgumentException.class,IllegalProductTypeException.class,
            IllegalPropertyTypeException.class})
    @ResponseStatus(code=HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiErrorResponse<List<StackTraceElement>> errorResponseForIllegalArgumentException(Exception ex) {
        ApiErrorResponse<List<StackTraceElement>> response = includeTraceForDebug ?
                new ApiErrorResponse<>(HttpStatus.BAD_REQUEST,ex) :
                new ApiErrorResponse<>(HttpStatus.BAD_REQUEST,ex.getMessage()) ;
        return response;
    }

    @ExceptionHandler({UnsupportedEncodingException.class})
    @ResponseStatus(code=HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiErrorResponse<List<StackTraceElement>> errorResponseForUnsupportedEncodingException(Exception ex) {
        ApiErrorResponse<List<StackTraceElement>> response = includeTraceForDebug ?
                new ApiErrorResponse<>(HttpStatus.BAD_REQUEST,ex) :
                new ApiErrorResponse<>(HttpStatus.BAD_REQUEST,ex.getMessage()) ;
        return response;
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(code=HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiErrorResponse<List<StackTraceElement>> errorResponseForException(Exception ex) {
        ApiErrorResponse<List<StackTraceElement>> response = includeTraceForDebug ?
                new ApiErrorResponse<>(HttpStatus.INTERNAL_SERVER_ERROR,ex) :
                new ApiErrorResponse<>(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage()) ;
        return response;
    }
}
