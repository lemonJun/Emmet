package com.takin.emmet.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

import com.google.common.util.concurrent.AbstractFuture;
import com.takin.emmet.concurrent.retry.Retryer.ResultAttempt;
import com.takin.emmet.concurrent.retry.StopStrategy;
import com.takin.emmet.concurrent.retry.WaitStrategy;

public class RetryableTask<V> extends AbstractFuture<V> implements RunnableFuture<V> {

    private final Sync sync;

    public RetryableTask(Callable<V> callable, ExecutorService executor, ScheduledExecutorService scheduler, StopStrategy stop, WaitStrategy wait) {

        this.sync = new Sync(callable, scheduler, executor, stop, wait);

    }

    public RetryableTask(final Runnable runnable, final V value, ExecutorService executor, ScheduledExecutorService scheduler, StopStrategy stop, WaitStrategy wait) {

        this(new Callable<V>() {
            @Override
            public V call() throws Exception {
                runnable.run();
                return value;
            }
        }, executor, scheduler, stop, wait);

    }

    protected void retry() {
        sync.innerRetry();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        if (sync.innerCancel(false)) {
            return super.cancel(mayInterruptIfRunning);
        }
        return false;
    }

    @Override
    protected void interruptTask() {
        sync.innerCancel(true);
    }

    @Override
    public void run() {
        sync.innerRun();
    }

    private class Sync extends AbstractQueuedSynchronizer {

        private final int READY = 1;
        private final int RUNNING = 2;
        private final int WAITING = 4;
        private final int RAN = 8;
        private final int CANCELLED = 16;

        private final Callable<V> callable;
        private final ScheduledExecutorService scheduler;
        private final ExecutorService executor;
        private final StopStrategy stop;
        private final WaitStrategy wait;

        private volatile Thread runner;
        private volatile Long startTime;
        private volatile int attempt = 0;

        private Sync(Callable<V> callable, ScheduledExecutorService scheduler, ExecutorService executor, StopStrategy stop, WaitStrategy wait) {

            this.callable = callable;
            this.scheduler = scheduler;
            this.executor = executor;
            this.stop = stop;
            this.wait = wait;

            setState(READY);

        }

        private boolean isCancelled(int state) {
            return (state & CANCELLED) != 0;
        }

        private boolean ranOrCancelled(int state) {
            return (state & (RAN | CANCELLED)) != 0;
        }

        private boolean readyOrWaiting(int state) {
            return (state & (READY | WAITING)) != 0;
        }

        void innerRetry() {

            int state = getState();

            if (isCancelled(state)) {
                return;
            }

            runner = null;

            long delay = System.nanoTime() - startTime;
            int previousAttempt = attempt++;

            //            if (stop.shouldStop(previousAttempt, delay)) {
            if (stop.shouldStop(new ResultAttempt(null, previousAttempt, delay))) {
                for (;;) {
                    state = getState();
                    if (isCancelled(state)) {
                        return;
                    }
                    if (compareAndSetState(state, RAN)) {
                        setException(new Exception("retries exceeded"));
                        break;
                    }
                }
            }

            long timeline = wait.computeSleepTime(new ResultAttempt(null, previousAttempt, delay));

            setState(WAITING);
            scheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    executor.submit(RetryableTask.this);
                }
            }, timeline, TimeUnit.MILLISECONDS);

        }

        void innerRun() {

            int state = getState();

            if (!readyOrWaiting(state)) {
                return;
            }

            if (!compareAndSetState(state, RUNNING)) {
                return;
            }

            if (startTime == null) {
                startTime = System.nanoTime();
            }

            runner = Thread.currentThread();

            V result;
            try {
                result = callable.call();
                for (;;) {
                    state = getState();
                    if (isCancelled(state)) {
                        return;
                    }
                    if (compareAndSetState(state, RAN)) {
                        set(result);
                        break;
                    }
                }
            } catch (InterruptedException e) {
            } catch (Throwable e) {
                runner = null;
                innerRetry();
            }

        }

        boolean innerCancel(boolean mayInterruptIfRunning) {
            for (;;) {
                int s = getState();
                if (ranOrCancelled(s)) {
                    return false;
                }
                if (compareAndSetState(s, CANCELLED)) {
                    break;
                }
            }
            if (mayInterruptIfRunning) {
                Thread r = runner;
                if (r != null) {
                    r.interrupt();
                }
            }
            return true;
        }

    }

}
