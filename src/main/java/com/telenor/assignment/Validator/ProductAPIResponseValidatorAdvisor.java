package com.telenor.assignment.Validator;

import com.telenor.assignment.dto.ProductGetDTO;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class ProductAPIResponseValidatorAdvisor implements ResponseBodyAdvice<Object> {

    @Override
    public Object beforeBodyWrite(final Object responseBodyObject, final MethodParameter methodParameter,
                                  final MediaType mediaType, final Class<? extends HttpMessageConverter<?>> aClass,
                                  final ServerHttpRequest serverHttpRequest,
                                  final ServerHttpResponse serverHttpResponse)
    {
        if (responseBodyObject instanceof ProductGetDTO){
            if(((ProductGetDTO) responseBodyObject).getData().size()==0)
            serverHttpResponse.setStatusCode(HttpStatus.NO_CONTENT);
        }
        return responseBodyObject;
    }

    @Override
    public boolean supports(final MethodParameter methodParameter, final Class<? extends HttpMessageConverter<?>> aClass)
    {
        return AbstractGenericHttpMessageConverter.class.isAssignableFrom(aClass);
    }
}
