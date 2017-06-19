package com.takin.emmet.concurrent;

import com.google.common.util.concurrent.Monitor;

public class SafeBox<V> {
    private final Monitor monitor = new Monitor();
    private final Monitor.Guard valuePresent = new Monitor.Guard(monitor) {
        public boolean isSatisfied() {
            return value != null;
        }
    };
    private final Monitor.Guard valueAbsent = new Monitor.Guard(monitor) {
        public boolean isSatisfied() {
            return value == null;
        }
    };
    
    private V value;

    public V get() throws InterruptedException {
        monitor.enterWhen(valuePresent);
        try {
            V result = value;
            value = null;
            return result;
        } finally {
            monitor.leave();
        }
    }

    public void set(V newValue) throws InterruptedException {
        monitor.enterWhen(valueAbsent);
        try {
            value = newValue;
        } finally {
            monitor.leave();
        }
    }
}
