package jvm;

import java.util.concurrent.atomic.AtomicReference;
import sun.misc.Unsafe;

public class TreiberStack<E> {
    AtomicReference<Node<E>> head = new AtomicReference<Node<E>>();

    public void push(E item) {
        Node<E> newHead = new Node<E>(item);
        Node<E> oldHead;
        do {
            oldHead = head.get();
            newHead.next = oldHead;
        } while (!head.compareAndSet(oldHead, newHead));
    }

    static class Node<E> {
        final E item;
        Node<E> next;

        public Node(E item) {
            this.item = item;
        }
    }

    //下面是一个自旋锁
    //    volatile int lock = 0;
    //
    //    public void lockAndDoSth() {
    //        for (;;) { // <-- 1. 循环
    //            if (lock == 1)
    //                continue;
    //            if (compareAndSet(lock, 0, 1)) { // <-- 2. CAS, 原子的 read-modify-write 指令
    //                // 已经获取锁, do sth        
    //                lock = 1; // 最后释放锁
    //            } else {
    //                Thread.yield(); // 获取锁失败, 主动出让 CPU
    //            }
    //        }
    //    }
}
