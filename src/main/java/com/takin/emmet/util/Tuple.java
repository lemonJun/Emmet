package com.takin.emmet.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;
import com.takin.emmet.reflect.CommonUtil;

public class Tuple<A, B> {

    final public A key;
    final public B value;

    public Tuple(A key, B value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o instanceof Tuple) {
            Tuple that = (Tuple) o;
            return CommonUtil.eq(that.key, key) && CommonUtil.eq(that.value, value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return CommonUtil.hc(key, value);
    }

    @Override
    public String toString() {
        return "T2(key: " + key + ", value: " + value + ")";
    }

    /**
     * Convert this {@code Tuple} instance into a Map with one key,value pair. Where
     * {@code key} is {@code key} and {@code value} is {@code value};
     * @return the map as described
     */
    public Map<A, B> asMap() {
        Map<A, B> m = new HashMap<A, B>();
        m.put(key, value);
        return m;
    }

    /**
     * Convert a list of {@code Tuple} instances into a Map. Where
     * {@code key} is {@code key} and {@code value} is {@code value};
     * <p>
     *     <b>Note</b> that the size of the returned map might be lesser than
     *     the size of the tuple list if there are multiple {@code key} has
     *     the same value, and the last one is the winner and it's {@code value}
     *     will be put into the map
     * </p>
     * @param <K> the key type
     * @param <V> the value type
     * @param list the list of tuples to be transformed into map
     * @return the map as described
     */
    public static <K, V> Map<K, V> asMap(Collection<Tuple<K, V>> list) {
        Map<K, V> m = Maps.newHashMap();
        for (Tuple<K, V> t : list) {
            m.put(t.key, t.value);
        }
        return m;
    }
}
