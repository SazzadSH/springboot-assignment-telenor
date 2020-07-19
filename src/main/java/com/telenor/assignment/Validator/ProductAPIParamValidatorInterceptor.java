package com.telenor.assignment.Validator;

import com.telenor.assignment.expection.IllegalProductTypeException;
import com.telenor.assignment.expection.IllegalPropertyTypeException;
import com.telenor.assignment.model.helper.ProductType;
import com.telenor.assignment.model.helper.PropertyType;
import com.telenor.assignment.util.Constants;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ProductAPIParamValidatorInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws IllegalProductTypeException,IllegalPropertyTypeException
    {
        String type = request.getParameter(Constants.TYPE);
        if(!StringUtils.isEmpty(type)){
            if(!ProductType.checkProductType(type)){
                throw new IllegalProductTypeException();
            }
        }
        String property = request.getParameter(Constants.PROPERTY);
        if(!StringUtils.isEmpty(property)){
            if(!PropertyType.checkPropertyType(property)){
                throw new IllegalPropertyTypeException();
            }
        }
        return true;

    }
}
