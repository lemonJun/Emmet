package com.takin.emmet.concurrent;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class OneSlotLatch {

    private static class Sycn extends AbstractQueuedSynchronizer {
        public Sycn() {
        }

        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            return super.tryRelease(arg);
        }

        @Override
        protected boolean isHeldExclusively() {
            return super.isHeldExclusively();
        }

    }

}
