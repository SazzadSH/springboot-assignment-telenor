package com.telenor.assignment.expection;

import com.telenor.assignment.util.Constants;

public class IllegalProductTypeException extends RuntimeException {
    public IllegalProductTypeException(){
        super(Constants.ILLEGAL_PRODUCT_TYPE_EXCEPTION_MESSAGE);
    }
}
