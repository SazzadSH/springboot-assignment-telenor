package com.telenor.assignment.helper.enummapper;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;


public class StringToEnumConverterFactory
        implements ConverterFactory<String, EnumWithValue<String>> {

    @Override
    public <T extends EnumWithValue<String>> Converter<String, T> getConverter(
            Class<T> targetType) {
        return new EnumValueConverter<String,T>(targetType);
    }
}
