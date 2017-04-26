package test.spinlock;

import java.util.concurrent.atomic.AtomicBoolean;

public class TASLock implements Lock {

    private final AtomicBoolean mutex = new AtomicBoolean(false);

    @Override
    public void lock() {
        while (mutex.getAndSet(true)) {

        }
    }

    @Override
    public void unlock() {
        mutex.set(false);
    }

    @Override
    public String toString() {
        return "TASLock [mutex=" + mutex + "]";
    }

}
