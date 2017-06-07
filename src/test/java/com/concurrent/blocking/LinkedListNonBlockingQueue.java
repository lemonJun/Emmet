package com.concurrent.blocking;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedListNonBlockingQueue<T> {

    private final LinkedList<T> list = new LinkedList<T>();

    private final ReentrantLock lock = new ReentrantLock();

    private final Condition full = lock.newCondition();
    private final Condition empty = lock.newCondition();

    private boolean isEmpty() {
        return list.size() == 0;
    }

    public void put(T t) {
        lock.lock();
        try {
            list.add(t);
            empty.signal();
        } catch (Exception e) {
        } finally {
            lock.unlock();
        }
    }

    public T get() {
        lock.lock();
        try {
            if (isEmpty()) {
                return null;
            }
            return list.getFirst();
        } catch (Exception e) {
        } finally {
            lock.unlock();
        }
        return null;
    }

}
