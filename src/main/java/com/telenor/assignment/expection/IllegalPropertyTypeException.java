package com.telenor.assignment.expection;

import static com.telenor.assignment.util.Constants.ILLEGAL_PROPERTY_TYPE_EXCEPTION_MESSAGE;

public class IllegalPropertyTypeException extends RuntimeException {
    public IllegalPropertyTypeException(){
        super(ILLEGAL_PROPERTY_TYPE_EXCEPTION_MESSAGE);
    }
}
