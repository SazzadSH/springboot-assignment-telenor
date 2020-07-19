package com.telenor.assignment.helper.enummapper;


import org.springframework.core.convert.converter.Converter;

public class EnumValueConverter<S extends java.io.Serializable, T extends EnumWithValue<S>>
        implements Converter<S, T> {

    private final Class<T> clazz;

    public EnumValueConverter(Class<T> clazz){
        this.clazz = clazz;
    }

    @Override
    public T convert(S value) {
        if (value == null) {
            return null;
        }
        for (T t : clazz.getEnumConstants()) {
            if (value.equals(t.getValue())) {
                return t;
            }
        }
        return null;
    }

}