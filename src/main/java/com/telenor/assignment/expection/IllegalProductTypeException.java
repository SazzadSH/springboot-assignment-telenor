package com.telenor.assignment.expection;

import static com.telenor.assignment.util.Constants.ILLEGAL_PRODUCT_TYPE_EXCEPTION_MESSAGE;

public class IllegalProductTypeException extends RuntimeException {
    public IllegalProductTypeException(){
        super(ILLEGAL_PRODUCT_TYPE_EXCEPTION_MESSAGE);
    }
}
