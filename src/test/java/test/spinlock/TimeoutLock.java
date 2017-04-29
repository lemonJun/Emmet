package test.spinlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/** 
 * 时限队列锁，支持tryLock超时操作 
 * QNode维护一个指针preNode指向前一个节点。当preNode == AVAILABLE表示已经释放锁。当preNode == null表示等待锁 
 * tail维护一个虚拟链表,通过tail.getAndSet方法获得前一个节点,并在前一个节点自旋,当释放锁时前一个节点的preNode == AVAIABLE，自动通知后一个节点获取锁 
 * 当一个节点超时或者被中断，那么它的前驱节点不为空。后续节点看到它的前驱节点不为空，并且不是AVAILABLE时，知道这个节点退出了，就会跳过它 
 * 当节点获得锁，进入临界区后，它的前驱节点可以被回收 
 * **/
public class TimeoutLock implements TryLock {
    // 声明为静态变量，防止被临时回收  

    private static final QNode AVAILABLE = new QNode();

    // 原子变量指向队尾  
    private AtomicReference<QNode> tail;

    ThreadLocal<QNode> myNode;

    public TimeoutLock() {
        tail = new AtomicReference<QNode>(null);
        myNode = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return new QNode();
            }
        };
    }

    @Override
    public void lock() {
        // 和CLHLock不同，每次新建一个Node，并设置给线程，目的是支持同一个线程可以多次获得锁，而不影响链中其他节点的状态  
        // CLHLock不需要每次新建Node是因为它使用了两个指针，一个指向前驱节点。而前驱节点释放后就可以回收了。  
        // CLHLock每次释放锁时设置myNode为失效的前驱节点，也是为了支持同一个线程可以多次获取锁而不影响其他节点  
        QNode node = new QNode();
        myNode.set(node);
        QNode pre = tail.getAndSet(node);
        if (pre != null) {
            // 在前一个节点自旋，当前一个节点是AVAILABLE时，表示它获得锁  
            while (pre.preNode != AVAILABLE) {

            }
        }
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        boolean isInterrupted = false;
        long startTime = System.currentTimeMillis();
        long duration = TimeUnit.MILLISECONDS.convert(time, unit);
        // 注意：每次tryLock都要new新的Node，为了同一个线程可以多次获得锁。如果每个线程都使用同一个节点，会影响链中其他的节点  
        QNode node = new QNode();
        myNode.set(node);
        // 尝试一次获取锁  
        QNode pre = tail.getAndSet(node);
        // 第一个节点或者之前的节点都是已经释放了锁的节点, pre==AVAILABLE表示获得了锁  
        if (pre == null || pre == AVAILABLE) {
            return true;
        }
        // 在给定时间内对preNode自旋  
        while ((System.currentTimeMillis() - startTime < duration) && !isInterrupted) {
            QNode predPreNode = pre.preNode;
            // 表示前一个节点已经释放了锁，设置了preNode域，否则preNode域为空  
            if (predPreNode == AVAILABLE) {
                return true;
            }
            // 当prePreNode ！= null时，只有两种情况，就是它超时了，或者被中断了。  
            // 跳过prePreNode不为空的节点，继续自旋它的下一个节点  
            else if (predPreNode != null) {
                pre = predPreNode;
            }
            if (Thread.interrupted()) {
                isInterrupted = true;
            }
        }

        // 超时或者interrupted，都要设置node的前驱节点不为空  
        node.preNode = pre;

        if (isInterrupted) {
            throw new InterruptedException();
        }

        return false;
    }

    @Override
    public void unlock() {
        QNode node = myNode.get();
        // CAS操作，如果为true，表示是唯一节点，直接释放就行；否则把preNode指向AVAILABLE  
        if (!tail.compareAndSet(node, null)) {
            node.preNode = AVAILABLE;
        }
    }

    public static class QNode {
        volatile QNode preNode;
    }
    
    public String toString() {
        return "TimeoutLock";
    }

}
