package com.nio;

import java.nio.channels.Selector;

public class SelectorTest {

    private static final int MAXSIZE = 5;

    public static void main(String[] args) {
        Selector[] sels = new Selector[MAXSIZE];
        try {
            for (int i = 0; i < MAXSIZE; ++i) {
                sels[i] = Selector.open();
            }
            Thread.sleep(300000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
