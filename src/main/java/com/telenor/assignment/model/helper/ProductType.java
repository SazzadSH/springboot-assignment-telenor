package com.telenor.assignment.model.helper;

import com.fasterxml.jackson.annotation.JsonValue;
import com.telenor.assignment.helper.EnumDbValue;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum ProductType implements EnumDbValue<String> {
    PHONE("phone"), SUBSCRIPTION("subscription");

    private String productType;

    private ProductType(String productType) {
        this.productType = productType;
    }

    public static ProductType of(String productType) {
        return Stream.of(ProductType.values())
                .filter(p -> p.getProductType().equals(productType))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public String getDbVal() {
        return productType;
    }
    @JsonValue
    final String value() {
        return productType;

    }
}
