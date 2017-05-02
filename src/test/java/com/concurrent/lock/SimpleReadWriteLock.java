package com.concurrent.lock;

import com.concurrent.spinlock.Lock;

public class SimpleReadWriteLock implements ReadWriteLock {

    @Override
    public Lock readLock() {
        return null;
    }

    @Override
    public Lock writeLock() {
        return null;
    }

}
