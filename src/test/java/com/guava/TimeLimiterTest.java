package com.guava;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.SimpleTimeLimiter;
import com.google.common.util.concurrent.TimeLimiter;

public class TimeLimiterTest {

    public static void main(String[] args) throws Exception {
        TimeLimiter timeLimiter = new SimpleTimeLimiter();
        timeLimiter.callWithTimeout(buildTask(), 5, TimeUnit.SECONDS, false);
    }

    private static Callable<Boolean> buildTask() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                long start = System.currentTimeMillis();
                long end = start;
                while (end - start <= 10 * 1000) {
                    end = System.currentTimeMillis();
                }

                return true;
            }
        };
    }
}
