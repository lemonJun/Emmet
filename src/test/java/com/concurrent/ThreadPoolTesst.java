package com.concurrent;

import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.RateLimiter;
import com.takin.emmet.concurrent.StandardThreadExecutor;

public class ThreadPoolTesst {

    public static void main(String[] args) {
        try {
            //            ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 15, 60, TimeUnit.SECONDS, new LinkedTransferQueue());
            StandardThreadExecutor executor = new StandardThreadExecutor(2, 5, 60, TimeUnit.SECONDS, 1000);
            RateLimiter limit = RateLimiter.create(8.0d);
            while (true) {
                try {
                    limit.acquire();
                    executor.submit(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                                System.out.println(Thread.currentThread().getName() + "--" + executor.getPoolSize() + "--" + executor.getQueue().size());
                            } catch (InterruptedException e) {
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
