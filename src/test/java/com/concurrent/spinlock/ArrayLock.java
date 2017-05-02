package com.concurrent.spinlock;

import java.util.concurrent.atomic.AtomicInteger;

/** 
 * 有界队列锁，使用一个volatile数组来组织线程 
 * 缺点是得预先知道线程的规模n，所有线程获取同一个锁的次数不能超过n 
 * 假设L把锁，那么锁的空间复杂度为O(Ln) 
 * **/
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
