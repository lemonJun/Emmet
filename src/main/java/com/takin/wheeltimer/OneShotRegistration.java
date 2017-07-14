package com.takin.wheeltimer;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

class OneShotRegistration<T> extends CompletableFuture<T> implements Registration<T> {

    private final Callable<T> callable;
    protected volatile int rounds; // rounds is only visible to one thread
    protected volatile Status status;

    private final long delay;

    public OneShotRegistration(int rounds, Callable<T> callable, long delay) {
        this.rounds = rounds;
        this.status = Status.READY;
        this.callable = callable;
        this.delay = delay;
    }

    @Override
    public void decrement() {
        rounds -= 1;
    }

    @Override
    public boolean ready() {
        // Check for READY here would be redundant, since if it was cancelled it'd be removed before this check
        return rounds == 0;
    }

    @Override
    public void reset() {
        throw new RuntimeException("One Shot Registrations can not be rescheduled");
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        this.status = Status.CANCELLED;
        return true;
    }

    @Override
    public boolean isCancelled() {
        return status == Status.CANCELLED;
    }

    @Override
    public int getOffset() {
        throw new RuntimeException("One Shot Registration can not be rescheduled");
    }

    @Override
    public boolean isCancelAfterUse() {
        return true;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return delay;
    }

    @Override
    public int rounds() {
        return rounds;
    }

    @Override
    public void run() {
        try {
            this.complete(callable.call());
        } catch (Exception e) {
            this.completeExceptionally(e);
        }
    }

}
