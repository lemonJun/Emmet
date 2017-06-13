package com.concurrent.lock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 
 *
 * @author WangYazhou
 * @date  2017年6月13日 下午12:00:09
 * @see
 */
public class MySemaphore {

    abstract static class Sync extends AbstractQueuedSynchronizer {
        Sync(int count) {
            setState(count);
        }

        final int nonfairTryAcquireShared(int acquires) {
            for (;;) {
                int c = getState();
                int remaining = c - acquires;
                if (remaining < 0 || compareAndSetState(c, remaining)) {
                    return remaining;
                }
            }
        }

        protected final boolean tryReleaseShared(int releases) {
            for (;;) {
                int current = getState();
                int next = current + releases;
                if (next < current) // overflow
                    throw new Error("Maximum permit count exceeded");
                if (compareAndSetState(current, next))
                    return true;
            }
        }
    }

    static final class NoFairSync extends Sync {
        private static final long serialVersionUID = 1L;

        NoFairSync(int count) {
            super(count);
        }

        @Override
        protected int tryAcquireShared(int arg) {
            return nonfairTryAcquireShared(arg);
        }

    }

    static final class FairSync extends Sync {
        FairSync(int count) {
            super(count);
        }

        @Override
        protected int tryAcquireShared(int arg) {
            for (;;) {
                //同步队列中有线程  就不要再等了
                if (hasQueuedPredecessors()) {
                    return -1;
                }
                int c = getState();
                int nextc = c - arg;
                if (nextc < 0 || compareAndSetState(c, nextc)) {
                    return nextc;
                }
            }
        }
    }

    private final Sync sync;

    public MySemaphore(int count) {
        sync = new NoFairSync(count);
    }

    public void acquire() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

    public void acquireUninterruptibly() {
        sync.acquireShared(1);
    }

    public boolean tryAcquire(int permit) {
        return sync.nonfairTryAcquireShared(permit) >= 0;
    }

    public boolean tryAcquire(int permit, long timeout, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(permit, unit.toNanos(timeout));
    }

    public void release() {
        sync.releaseShared(1);
    }

    public static void main(String[] args) {
        final MySemaphore semaphore = new MySemaphore(3);
        //        final Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 11; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    semaphore.acquireUninterruptibly();
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " sema");
                    semaphore.release();
                }
            }).start();
        }
    }
}
