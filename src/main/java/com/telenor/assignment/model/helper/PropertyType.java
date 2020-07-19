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
            lookup.put(item.getValue(), item);
    }

    @JsonValue
    @Override
    public String getValue() {
        return propertyType;
    }

    public static Boolean hasPropertyType(String value) {
        return lookup.get(value)!=null;
    }

    public static PropertyType getPropertyType(String value) {
        return Optional.ofNullable(lookup.get(value))
                .orElseThrow(IllegalPropertyTypeException::new);
    }
}
