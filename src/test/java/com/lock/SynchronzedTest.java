package com.lock;

public class SynchronzedTest {

    public synchronized void synmethond() {
        System.out.println("m");
    }

    public void synblock() {
        synchronized (SynchronzedTest.class) {
            System.out.println("b");
        }
    }

}
