package com.concurrent.spinlock;

import java.util.concurrent.atomic.AtomicBoolean;

public class TTASLock implements Lock {

    private AtomicBoolean mutex = new AtomicBoolean(false);

    @Override
    public void lock() {
        while (true) {
            while (mutex.get()) {
            }

            if (!mutex.getAndSet(true)) {
                return;
            }
        }
    }

    @Override
    public void unlock() {
        mutex.set(false);
    }

    @Override
    public String toString() {
        return "TTASLock [mutex=" + mutex + "]";
    }

}
