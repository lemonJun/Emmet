package com.concurrent.spinlock;

import java.util.concurrent.atomic.AtomicReference;

/** 
 * 无界队列锁，使用一个链表来组织线程 
 * 假设L把锁，n个线程，那么锁的空间复杂度为O(L+n) 
 * 
 * 
 */
public class CLHLock implements Lock {

    private AtomicReference<QNode> tail;

    ThreadLocal<QNode> mynode;

    ThreadLocal<QNode> mypronode;

    public CLHLock() {
        tail = new AtomicReference<QNode>(new QNode());
        mynode = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return new QNode();
            }
        };
        mypronode = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return null;
            }
        };
    }

    public static class QNode {
        volatile boolean lock;
    }

    @Override
    public void lock() {
        QNode node = mynode.get();
        node.lock = true;
        QNode prenode = tail.getAndSet(node);
        mypronode.set(prenode);
        while (prenode.lock) {

        }
    }

    @Override
    public void unlock() {
        QNode node = mynode.get();
        node.lock = false;
        mynode.set(mypronode.get());
    }

    public String toString() {
        return "CLHLock";
    }

}
