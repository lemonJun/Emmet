package test.spinlock;

import java.util.concurrent.atomic.AtomicReference;

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
