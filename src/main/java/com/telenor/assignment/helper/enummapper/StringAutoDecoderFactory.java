package com.telenor.assignment.helper.enummapper;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

public class StringAutoDecoderFactory
        implements ConverterFactory<String, String> {
    @Override
    public <T extends String> Converter<String, T> getConverter(Class<T> aClass) {
        return new StringAutoURLDecoder();
    }
}
