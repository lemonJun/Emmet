package com.fiber;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.strands.Strand;

public class Example {

    public static void main(String[] args) {
        try {
            int FiberNumber = 1_000_000;
            CountDownLatch latch = new CountDownLatch(1);
            AtomicInteger counter = new AtomicInteger(0);

            for (int i = 0; i < FiberNumber; i++) {
                new Fiber(() -> {
                    counter.incrementAndGet();
                    if (counter.get() == FiberNumber) {
                        System.out.println("done");
                    }
                    Strand.sleep(1000000);
                }).start();
            }
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
