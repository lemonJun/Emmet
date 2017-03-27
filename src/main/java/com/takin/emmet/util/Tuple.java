package com.takin.emmet.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;
import com.takin.emmet.reflect.CommonUtil;

public class Tuple<A, B> {

    final public A _1;
    final public B _2;

    public Tuple(A _1, B _2) {
        this._1 = _1;
        this._2 = _2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o instanceof Tuple) {
            Tuple that = (Tuple) o;
            return CommonUtil.eq(that._1, _1) && CommonUtil.eq(that._2, _2);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return CommonUtil.hc(_1, _2);
    }

    @Override
    public String toString() {
        return "T2(_1: " + _1 + ", _2: " + _2 + ")";
    }

    /**
     * Convert this {@code Tuple} instance into a Map with one key,value pair. Where
     * {@code key} is {@code _1} and {@code value} is {@code _2};
     * @return the map as described
     */
    public Map<A, B> asMap() {
        Map<A, B> m = new HashMap<A, B>();
        m.put(_1, _2);
        return m;
    }

    /**
     * Convert a list of {@code Tuple} instances into a Map. Where
     * {@code key} is {@code _1} and {@code value} is {@code _2};
     * <p>
     *     <b>Note</b> that the size of the returned map might be lesser than
     *     the size of the tuple list if there are multiple {@code _1} has
     *     the same value, and the last one is the winner and it's {@code _2}
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
            m.put(t._1, t._2);
        }
        return m;
    }
}
