package com.telenor.assignment.helper.enummapper;

import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;

import java.net.URLDecoder;

public class StringAutoURLDecoder<T extends String> implements Converter<String, T> {
    @SneakyThrows
    @Override
    public T convert(String s) {
        return (T) URLDecoder.decode(s, "UTF-8");
    }
}
