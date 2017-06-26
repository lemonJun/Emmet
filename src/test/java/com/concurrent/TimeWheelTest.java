package com.concurrent;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.RateLimiter;
import com.takin.emmet.algo.HashedWheelTimer;
import com.takin.emmet.algo.Timeout;
import com.takin.emmet.algo.TimerTask;

public class TimeWheelTest {
    public static void main(String[] args) {

        HashedWheelTimer timer = new HashedWheelTimer();
        RateLimiter limit = RateLimiter.create(1.0d);

        Random random = new Random();

        for (int i = 0; i < 2; i++) {
            limit.acquire();
            int delay = random.nextInt(20);
            System.out.println("new:" + System.currentTimeMillis() / 1000 + " delay " + delay);
            timer.newTimeout(new TimerTask() {
                @Override
                public void run(Timeout timeout) throws Exception {
                    System.out.println(Thread.currentThread().getName() + "--" + System.currentTimeMillis() / 1000);
                }
            }, delay, TimeUnit.SECONDS);
        }
    }

}
