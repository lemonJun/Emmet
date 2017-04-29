package test.spinlock;

import java.util.concurrent.atomic.AtomicInteger;

public class ArrayLock implements Lock {

    private volatile boolean[] flags;

    private AtomicInteger tail;

    private final int capacity;

    private ThreadLocal<Integer> mySlotIndex = new ThreadLocal<Integer>() {
        protected Integer initialValue() {
            return 0;
        }
    };

    public ArrayLock(int capacity) {
        this.capacity = capacity;
        flags = new boolean[capacity];
        tail = new AtomicInteger(0);
        // 默认第一个位置可获得锁  
        flags[0] = true;
    }

    @Override
    public void lock() {
        int slot = tail.getAndIncrement() % capacity;
        mySlotIndex.set(slot);
        while (!flags[slot]) {

        }
    }

    @Override
    public void unlock() {
        int slot = mySlotIndex.get();
        flags[slot] = false;
        flags[(slot + 1) % capacity] = true;
    }

    public String toString() {
        return "ArrayLock";
    }

}
