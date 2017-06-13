package com.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class MyCountDownlatch {

    static class Sync extends AbstractQueuedSynchronizer {

        Sync(int count) {
            setState(count);
        }

        //获取到锁    在此之前线程在此阻塞
        @Override
        protected int tryAcquireShared(int arg) {
            return getState() == 0 ? 1 : -1;
        }

        //
        @Override
        protected boolean tryReleaseShared(int arg) {
            for (;;) {
                int c = getState();
                if (c == 0) {
                    return false;
                }
                int nextc = c - 1;
                if (compareAndSetState(c, nextc)) {
                    return true;
                }
            }
        }
    }

    private final Sync sync;

    public MyCountDownlatch(int count) {
        sync = new Sync(count);
    }

    //参数没有作用
    public void await() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

    public void await(long timeout, TimeUnit unit) throws InterruptedException {
        sync.tryAcquireSharedNanos(1, unit.toNanos(timeout));
    }

    public void countdown() {
        sync.releaseShared(1);
    }

    public static void main(String[] args) {
        try {
            final MyCountDownlatch latch = new MyCountDownlatch(3);
            for (int i = 0; i < 2; i++) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + "finish");
                        latch.countdown();
                    }
                }).start();
            }
            //            latch.await();
            //            System.out.println("finish");
            latch.await(5, TimeUnit.SECONDS);
            System.out.println("timeout finish");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
