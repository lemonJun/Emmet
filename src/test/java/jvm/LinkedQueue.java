package jvm;

import java.util.concurrent.atomic.AtomicReference;

public class LinkedQueue<E> {
    private static class Node<E> {
        final E item;
        final AtomicReference<Node<E>> next;

        Node(E item, Node<E> next) {
            this.item = item;
            this.next = new AtomicReference<Node<E>>(next);
        }
    }

    private AtomicReference<Node<E>> head = new AtomicReference<Node<E>>(new Node<E>(null, null));
    private AtomicReference<Node<E>> tail = head;

    public boolean put(E item) {
        Node<E> newNode = new Node<E>(item, null);
        while (true) {
            Node<E> curTail = tail.get();
            Node<E> tailNext = curTail.next.get();
            if (curTail == tail.get()) {
                if (tailNext == null) { // 看到的是静止状态
                    if (curTail.next.compareAndSet(null, newNode)) { // A 的第一个CAS
                        tail.compareAndSet(curTail, newNode); // A 的第二个CAS，不用判断结果，因为如果失败了，说明B（或其他线程）帮它完成了这个动作
                        return true;
                    }
                } else { // 看到中间状态
                    tail.compareAndSet(curTail, tailNext); // B 帮 A 完成第二个更新动作
                }
            }
        }
    }
}
