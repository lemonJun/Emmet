package com.takin.emmet.log.factory;

import java.util.Iterator;
import java.util.Map.Entry;

import com.takin.emmet.log.log.RollLog;

public class LogShutDown implements Runnable {
    private Iterator<Entry<String, RollLog>> iterators;

    public LogShutDown(Iterator<Entry<String, RollLog>> iterators) {
        this.iterators = iterators;
    }

    public void run() {
        while (iterators.hasNext()) {
            iterators.next().getValue().release();
        }
    }
}
