package com.takin.emmet.concurrent;

import static com.google.common.util.concurrent.MoreExecutors.listeningDecorator;
import static java.util.concurrent.Executors.newScheduledThreadPool;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

import com.google.common.util.concurrent.ForwardingListeningExecutorService;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.takin.emmet.concurrent.retry.StopStrategy;
import com.takin.emmet.concurrent.retry.WaitStrategy;

public class RetryingExecutorService extends ForwardingListeningExecutorService {

    private final ListeningExecutorService executor;
    private final ScheduledExecutorService scheduler;

    public RetryingExecutorService(ListeningExecutorService executor, ScheduledExecutorService scheduler) {
        this.executor = executor;
        this.scheduler = scheduler;
    }

    public RetryingExecutorService(ExecutorService executor, ScheduledExecutorService scheduler) {
        this(listeningDecorator(executor), listeningDecorator(scheduler));
    }

    public RetryingExecutorService(ListeningExecutorService executor) {
        this(executor, listeningDecorator(newScheduledThreadPool(1)));
    }

    public RetryingExecutorService(ExecutorService executor) {
        this(listeningDecorator(executor), listeningDecorator(newScheduledThreadPool(1)));
    }

    @Override
    protected ListeningExecutorService delegate() {
        return executor;
    }

    public <V> ListenableFuture<V> submit(Callable<V> callable, WaitStrategy wait, StopStrategy stop) {
        RetryableTask<V> task = new RetryableTask<V>(callable, executor, scheduler, stop, wait);
        execute(task);
        return task;
    }

    public ListenableFuture<?> submit(Runnable runnable, WaitStrategy wait, StopStrategy stop) {
        RetryableTask<?> task = new RetryableTask<Object>(runnable, null, executor, scheduler, stop, wait);
        execute(task);
        return task;
    }

    public <V> ListenableFuture<V> submit(Runnable runnable, V result, WaitStrategy wait, StopStrategy stop) {
        RetryableTask<V> task = new RetryableTask<V>(runnable, result, executor, scheduler, stop, wait);
        execute(task);
        return task;
    }

    public void shutdown() {
        scheduler.shutdown();
        executor.shutdown();
    }

    public List<Runnable> shutdownNow() {
        scheduler.shutdownNow();
        return executor.shutdownNow();
    }

}
