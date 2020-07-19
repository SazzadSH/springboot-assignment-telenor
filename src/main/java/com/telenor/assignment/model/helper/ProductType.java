package com.telenor.assignment.model.helper;

import com.fasterxml.jackson.annotation.JsonValue;
import com.telenor.assignment.expection.IllegalProductTypeException;
import com.telenor.assignment.helper.enummapper.EnumWithValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;


public enum ProductType implements EnumWithValue<String> {
    PHONE("phone"), SUBSCRIPTION("subscription");

    private String productType;

    private static final Map<String, ProductType> lookup = new HashMap<>();

    private ProductType(String productType) {
        this.productType = productType;
    }

    static {
        for (ProductType item : ProductType.values())
            lookup.put(item.getStringValue(), item);
    }

    @JsonValue
    public final String getStringValue() {
        return productType;
    }

    public static ProductType of(String productType) {
        return Stream.of(ProductType.values())
                .filter(p -> p.getStringValue().equals(productType))
                .findFirst()
                .orElseThrow(IllegalProductTypeException::new);
    }

    @Override
    public String getValue() {
        return productType;
    }

    public static ProductType getProductType(String value) {
        return Optional.ofNullable(lookup.get(value))
                .orElseThrow(IllegalProductTypeException::new);
    }

}
