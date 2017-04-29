package test.spinlock;

import java.util.concurrent.TimeUnit;

public interface TryLock {
    void lock();

    boolean tryLock(long time, TimeUnit unit) throws InterruptedException;

    void unlock();

}
