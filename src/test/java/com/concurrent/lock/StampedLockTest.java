package com.concurrent.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

public class StampedLockTest {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Map<String, String> map = new HashMap<>();
        StampedLock lock = new StampedLock();

        executor.submit(() -> {
            long stamp = lock.writeLock();
            try {
                map.put("foo", "bar");
            } finally {
                lock.unlockWrite(stamp);
            }
        });

        executor.submit(() -> {
            long stamp = lock.tryOptimisticRead();
            try {
                System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
                System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
                System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
            } finally {
                lock.unlock(stamp);
            }
        });

        Runnable readTask = () -> {
            long stamp = lock.tryOptimisticRead();//乐观读
            try {
                System.out.println(map.get("foo"));
            } finally {
                lock.unlockRead(stamp);
            }
        };

        //        executor.submit(() -> {
        //            long stamp = lock.readLock();
        //            try {
        //                if (count == 0) {
        //                    stamp = lock.tryConvertToWriteLock(stamp);
        //                    if (stamp == 0L) {
        //                        System.out.println("Could not convert to write lock");
        //                        stamp = lock.writeLock();
        //                    }
        //                    count = 23;
        //                }
        //                System.out.println(count);
        //            } finally {
        //                lock.unlock(stamp);
        //            }
        //        });

        executor.submit(readTask);
        executor.submit(readTask);

    }
}
