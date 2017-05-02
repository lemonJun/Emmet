package com.concurrent.blocking;

public class BlockingArray<T> {

    private final T[] array;

    private int head;

    private int tail;

    private int count;

    public BlockingArray(int size) {
        array = (T[]) new Object[size];
    }

    public synchronized void put(T item) throws InterruptedException {
        while (isFull()) {
            wait();
        }
        array[tail] = item;
        System.out.println("put-" + item);

        if (++tail == array.length) {
            tail = 0;
        }
        count++;
        notifyAll();
    }

    public synchronized T take() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
        Object obj = array[head];
        System.out.println("take-" + obj);
        if (++head == array.length) {
            head = 0;
        }
        count--;
        notifyAll();
        return (T) obj;
    }

    private boolean isFull() {
        return count == array.length;
    }

    private boolean isEmpty() {
        return count == 0;
    }

}
