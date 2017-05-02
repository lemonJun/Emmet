package com.concurrent.spinlock;

public interface Lock {
    public void lock();

    public void unlock();
}
