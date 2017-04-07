package com.takin.emmet.algo;

import java.util.Iterator;

import com.google.common.base.Preconditions;


/**
 * Created by shenshijun on 15/2/3.
 */
public interface Map<K, V> {
    void set(K key, V value);

    V get(K key);

    public default V getDefault(K key, V default_value) {
        Preconditions.checkNotNull(key);
        V value = get(key);
        return (value == null) ? default_value : value;
    }

    public default V setIfAbsent(K key, V default_value){
        Preconditions.checkNotNull(key);
        V value = get(key);
        if (value == null) {
            set(key, default_value);
            value = default_value;
        }
        return value;
    }

    public int size();

    boolean containsKey(K key);

    MapIterator<K, V> iterator();

    Iterator<K> keyIterator();

    Iterator<V> valueIterator();

    public default boolean isEmpty() {
        return size() <= 0;
    }

    V remove(K key);
}


