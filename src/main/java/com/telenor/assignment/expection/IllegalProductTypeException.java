package com.telenor.assignment.expection;

import com.telenor.assignment.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class IllegalProductTypeException extends RuntimeException {
    public IllegalProductTypeException(){
        super(Constants.ILLEGAL_PRODUCT_TYPE_EXCEPTION_MESSAGE);
    }
}
