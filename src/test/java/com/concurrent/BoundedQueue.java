package com.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedQueue<T> {
    private final T[] array;

    private int head;
    private int tail;
    private int count;

    private final ReentrantLock lock = new ReentrantLock();

    private Condition full = lock.newCondition();

    private Condition empty = lock.newCondition();

    public BoundedQueue(int size) {
        array = (T[]) new Object[size];
    }

    public void put(T item) throws InterruptedException {
        if (lock.tryLock()) {
            try {
                while (isFull()) {
                    full.await();
                }

                array[tail] = item;

                if (++tail == array.length) {
                    tail = 0;
                }
                count++;
                empty.signal();
            } catch (Exception e) {
            } finally {
                lock.unlock();
            }
        }

    }

    public T take() throws InterruptedException {
        if (lock.tryLock()) {
            try {
                while (isEmpty()) {
                    empty.await();
                }
                Object obj = array[head];
                if (++head == array.length) {
                    head = 0;
                }
                count--;
                full.signal();
                return (T) obj;
            } catch (Exception e) {
            } finally {
                lock.unlock();
            }
        }

        return null;
    }

    private boolean isFull() {
        return count == array.length;
    }

    private boolean isEmpty() {
        return count == 0;
    }
}
