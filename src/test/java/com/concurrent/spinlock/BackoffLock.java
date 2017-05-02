package com.concurrent.spinlock;

import java.util.concurrent.atomic.AtomicBoolean;

public class BackoffLock implements Lock {

    private final int MIN_DELAY, MAX_DELAY;

    public BackoffLock(int min, int max) {
        MIN_DELAY = min;
        MAX_DELAY = max;
    }

    private AtomicBoolean mutex = new AtomicBoolean(false);

    @Override
    public void lock() {
        // 增加回退对象  
        Backoff backoff = new Backoff(MIN_DELAY, MAX_DELAY);
        while (true) {
            // 第一步使用读操作，尝试获取锁，当mutex为false时退出循环，表示可以获取锁  
            while (mutex.get()) {
            }
            // 第二部使用getAndSet方法来尝试获取锁  
            if (!mutex.getAndSet(true)) {
                return;
            } else {
                //回退  
                try {
                    backoff.backoff();
                } catch (InterruptedException e) {
                }
            }

        }
    }

    @Override
    public void unlock() {
        mutex.set(false);
    }

    @Override
    public String toString() {
        return "BackoffLock [MIN_DELAY=" + MIN_DELAY + ", MAX_DELAY=" + MAX_DELAY + ", mutex=" + mutex + "]";
    }

}
