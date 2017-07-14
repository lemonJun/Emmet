package com.takin.emmet.concurrent;

import java.util.concurrent.Semaphore;

public final class AdjustableSemaphore {

    private final ResizeableSemaphore semaphore;

    private int maxPermits = 0;

    public AdjustableSemaphore(int size) {
        semaphore = new ResizeableSemaphore(size);
    }

    public synchronized void setMaxPermits(int newMax) {
        if (newMax < 1) {
            throw new IllegalArgumentException("Semaphore size must be at least 1," + " was " + newMax);
        }

        int delta = newMax - this.maxPermits;

        if (delta == 0) {
            return;
        } else if (delta > 0) {
            this.semaphore.release(delta);
        } else {
            delta *= -1;
            this.semaphore.reducePermits(delta);
        }
        
        this.maxPermits = newMax;
    }

    public void release() {
        this.semaphore.release();
    }

    public void release(int numPermits) {
        this.semaphore.release(numPermits);
    }

    public void acquire() throws InterruptedException {
        this.semaphore.acquire();
    }

    public synchronized int getMaxPermits() {
        return maxPermits;
    }

    private static final class ResizeableSemaphore extends Semaphore {

        private static final long serialVersionUID = 1L;

        ResizeableSemaphore(int size) {
            super(size);
        }

        @Override
        protected void reducePermits(int reduction) {
            super.reducePermits(reduction);
        }
    }
}