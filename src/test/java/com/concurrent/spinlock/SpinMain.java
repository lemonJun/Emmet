package com.concurrent.spinlock;

public class SpinMain {
    //    private static TimeCost timeCost = new TimeCost(new TASLock());

    //    private static TimeCost timeCost = new TimeCost(new TTASLock());

    private static final Lock lock = new CLHLock();

    private static volatile int value = 0;

    public static void method() {
        lock.lock();
        //int a = 10;  
        System.out.println("Value: " + ++value);

        lock.unlock();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    method();
                }
            });
            t.start();
        }
    }
}
