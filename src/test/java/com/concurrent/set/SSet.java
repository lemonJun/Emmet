package com.concurrent.set;

public interface SSet<T> {

    boolean add(T t);

    boolean remove(T t);

    boolean contains(T t);

}
