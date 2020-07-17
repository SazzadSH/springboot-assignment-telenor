package com.telenor.assignment.dto.helper;

import com.fasterxml.jackson.annotation.JsonValue;
import com.telenor.assignment.expection.IllegalProductTypeException;
import com.telenor.assignment.helper.enummapper.EnumDbValue;
import com.telenor.assignment.model.helper.ProductType;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Getter
public enum Property implements EnumDbValue<String> {
    COLOR("color"),
    GB_LIMIT("gb_limit");

    private String propertyType;

    private static final Map<String, Property> lookup = new HashMap<>();

    private Property(String propertyType) {
        this.propertyType = propertyType;
    }

    static {
        for (Property item : Property.values())
            lookup.put(item.getValue(), item);
    }

    @JsonValue
    private final String getValue() {
        return propertyType;
    }

    public static ProductType of(String productType) {
        return Stream.of(ProductType.values())
                .filter(p -> p.getProductType().equals(productType))
                .findFirst()
                .orElseThrow(IllegalProductTypeException::new);
    }

    @Override
    public String getDbVal() {
        return propertyType;
    }

    public static Property getProductType(String value) {
        return Optional.ofNullable(lookup.get(value))
                .orElseThrow(IllegalProductTypeException::new);
    }
}
