package com.concurrent.set;

import java.util.concurrent.locks.ReentrantLock;

public class CoarseSet<T> implements SSet<T> {

    private final Node<T> head;
    private final ReentrantLock lock = new ReentrantLock();

    public CoarseSet() {
        head = new Node<T>();
        head.key = Integer.MIN_VALUE;
        Node<T> max = new Node<T>();
        max.key = Integer.MAX_VALUE;
        head.next = max;
    }

    @Override
    public boolean add(T t) {
        return false;
    }

    @Override
    public boolean remove(T t) {
        return false;
    }

    @Override
    public boolean contains(T t) {
        return false;
    }

}
