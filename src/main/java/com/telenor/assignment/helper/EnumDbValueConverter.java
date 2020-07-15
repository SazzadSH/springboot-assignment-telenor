package com.telenor.assignment.helper;

import javax.persistence.AttributeConverter;

public abstract class EnumDbValueConverter<T extends java.io.Serializable, E extends Enum<E> & EnumDbValue<T>>
        implements AttributeConverter<E, T> {

    private final Class<E> clazz;

    public EnumDbValueConverter(Class<E> clazz){
        this.clazz = clazz;
    }

    @Override
    public T convertToDatabaseColumn(E attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getDbVal();
    }

    @Override
    public E convertToEntityAttribute(T dbData) {
        if (dbData == null) {
            return null;
        }
        for (E e : clazz.getEnumConstants()) {
            if (dbData.equals(e.getDbVal())) {
                return e;
            }
        }
        return null;
    }

}