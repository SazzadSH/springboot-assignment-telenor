package com.telenor.assignment.exception;

import com.telenor.assignment.expection.IllegalProductTypeException;
import com.telenor.assignment.expection.IllegalPropertyTypeException;
import com.telenor.assignment.model.Product;
import com.telenor.assignment.model.helper.ProductType;
import com.telenor.assignment.model.helper.PropertyType;
import com.telenor.assignment.utils.Utils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

public class TestExceptionThrows {

    @Test
    public void getProductTypeWithUnknown_expectIllegalProductTypeException()
    {
        assertThrows(IllegalProductTypeException.class, () -> ProductType.getProductType("Unknown"));
    }

    @Test
    public void getPropertyTypeWithUnknown_expectIllegalPropertyTypeException()
    {
        assertThrows(IllegalPropertyTypeException.class, () -> PropertyType.getPropertyType("Unknown"));
    }
}
