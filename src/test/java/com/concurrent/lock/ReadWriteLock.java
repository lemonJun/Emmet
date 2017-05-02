package com.concurrent.lock;

import com.concurrent.spinlock.Lock;

public interface ReadWriteLock {
    public Lock readLock();

    public Lock writeLock();
}
