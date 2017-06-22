package com.concurrent;

import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.RateLimiter;
import com.takin.emmet.algo.HashedWheelTimer;
import com.takin.emmet.algo.Timeout;
import com.takin.emmet.algo.TimerTask;

public class TimeWheelTest {
    public static void main(String[] args) {

        HashedWheelTimer timer = new HashedWheelTimer();
        RateLimiter limit = RateLimiter.create(8.0d);

        while (true) {
            limit.acquire();
            timer.newTimeout(new TimerTask() {
                @Override
                public void run(Timeout timeout) throws Exception {
                    System.out.println(Thread.currentThread().getName() + "--" + System.currentTimeMillis());
                }
            }, 5, TimeUnit.SECONDS);
        }
    }

}
