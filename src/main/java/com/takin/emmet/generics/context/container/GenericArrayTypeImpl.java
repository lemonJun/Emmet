package com.takin.emmet.generics.context.container;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.Collections;

import com.takin.emmet.generics.util.TypeToStringUtils;

/**
 * Wrapper to hold resolved array type.
 *
 * @author Vyacheslav Rusakov
 * @since 15.12.2014
 */
public class GenericArrayTypeImpl implements GenericArrayType {

    private final Type componentType;

    public GenericArrayTypeImpl(final Type componentType) {
        this.componentType = componentType;
    }

    @Override
    public Type getGenericComponentType() {
        return componentType;
    }

    @Override
    public boolean equals(final Object o) {
        boolean res = this == o;
        if (!res && o instanceof GenericArrayType) {
            final Type thatComponentType = ((GenericArrayType) o).getGenericComponentType();
            res = componentType != null ? componentType.equals(thatComponentType) : thatComponentType == null;
        }
        return res;
    }

    @Override
    public int hashCode() {
        return componentType != null ? componentType.hashCode() : 0;
    }

    @Override
    public String toString() {
        return TypeToStringUtils.toStringType(this, Collections.<String, Type>emptyMap());
    }
}
