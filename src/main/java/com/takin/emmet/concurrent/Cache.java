package com.takin.emmet.concurrent;

public interface Cache<K, V> {
    public V put(K key, V val);

    public V get(K key);

    public V remove(K key);

    public void clear();

}
