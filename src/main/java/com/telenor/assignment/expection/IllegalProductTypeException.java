package com.telenor.assignment.expection;

import com.telenor.assignment.util.Constants;

import static com.telenor.assignment.util.Constants.ILLEGAL_PRODUCT_TYPE_EXCEPTION_MESSAGE;

public class IllegalProductTypeException extends RuntimeException {
    public IllegalProductTypeException(){
        super(ILLEGAL_PRODUCT_TYPE_EXCEPTION_MESSAGE);
    }
}
