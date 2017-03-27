package com.takin.emmet.concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 限制资源访问的并发数  是说一个资源每次只能由一个资源来访问吗？？
 * 使用布尔原子变量，信号量保证只释放一次
 */
public class SemaphoreOnce {
    private final AtomicBoolean released = new AtomicBoolean(false);
    private final Semaphore semaphore;

    public SemaphoreOnce(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public void release() {
        if (this.semaphore != null) {
            if (this.released.compareAndSet(false, true)) {
                this.semaphore.release();
            }
        }
    }
    
    public Semaphore getSemaphore() {
        return semaphore;
    }
}
