package com.telenor.assignment.model.helper;

import com.fasterxml.jackson.annotation.JsonValue;
import com.telenor.assignment.expection.IllegalPropertyTypeException;
import com.telenor.assignment.helper.enummapper.EnumWithValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public enum PropertyType implements EnumWithValue<String> {
    COLOR("color"),
    GB_LIMIT("gb_limit");

    private String propertyType;

    private static final Map<String, PropertyType> lookup = new HashMap<>();

    private PropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    static {
        for (PropertyType item : PropertyType.values())
            lookup.put(item.getStringValue(), item);
    }

    @JsonValue
    public final String getStringValue() {
        return propertyType;
    }

    @Override
    public String getValue() {
        return propertyType;
    }

    public static Boolean checkPropertyType(String value) {
        return Optional.ofNullable(lookup.get(value)!=null)
                .orElseThrow(IllegalPropertyTypeException::new);
    }
}
