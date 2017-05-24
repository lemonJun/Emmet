package com.takin.emmet.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomThreadFactory implements ThreadFactory {

    private final String threadPrefix;

    private final AtomicInteger count = new AtomicInteger(1);

    public CustomThreadFactory(String poolname) {
        threadPrefix = String.format("TP-%s-", poolname);
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, String.format("%s-%d", threadPrefix, count.getAndIncrement()));
    }

}
