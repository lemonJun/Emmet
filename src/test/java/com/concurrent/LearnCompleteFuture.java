package com.concurrent;

import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *  实现的功能是：只要有AB一个任务完成了，就执行C任务
 *  
 * @author WangYazhou
 * @date  2017年6月19日 下午6:13:54
 * @see
 */
public class LearnCompleteFuture {
    private static Random random = new Random();

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        useFuture();

        TimeUnit.SECONDS.sleep(10);
        System.out.println();

        useCompletableFuture();
    }

    private static void useFuture() throws InterruptedException, ExecutionException {
        System.out.println("Future");
        ExecutorService exector = Executors.newFixedThreadPool(3);
        Future<Void> futureA = exector.submit(() -> work("A1"));
        Future<Void> futureB = exector.submit(() -> work("B1"));
        while (true) {
            try {
                futureA.get(1, TimeUnit.SECONDS);
                break;
            } catch (TimeoutException e) {
            }
            try {
                futureB.get(1, TimeUnit.SECONDS);
                break;
            } catch (TimeoutException e) {
            }
        }
        exector.submit(() -> work("C1")).get();
        exector.shutdown();
    }

    private static void useCompletableFuture() throws InterruptedException, ExecutionException {
        System.out.println("CompletableFuture");
        CompletableFuture<Void> futureA = CompletableFuture.runAsync(() -> work("A2"));
        CompletableFuture<Void> futureB = CompletableFuture.runAsync(() -> work("B2"));
        futureA.runAfterEither(futureB, () -> work("C2")).get();
    }

    public static Void work(String name) {
        System.out.println(name + " starts at " + LocalTime.now());
        try {
            TimeUnit.SECONDS.sleep(random.nextInt(10));
        } catch (InterruptedException e) {
        }
        System.out.println(name + " ends at " + LocalTime.now());
        return null;
    }
}
