package com.telenor.assignment.helper;

import com.telenor.assignment.model.helper.ProductType;

public class ProductTypeConverter extends EnumDbValueConverter<String, ProductType> {
    public ProductTypeConverter() {
        super(ProductType.class);
    }
}
