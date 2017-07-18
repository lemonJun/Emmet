//package com.concurrent.spinlock;
//
//import java.util.concurrent.atomic.AtomicReference;
//
///** 
// * 无界队列锁，使用一个链表来组织线程 
// * 假设L把锁，n个线程，那么锁的空间复杂度为O(L+n) 
// * 
// */
//public class MCSLock implements Lock {
//
//    private AtomicReference<QNode> tail;
//
//    ThreadLocal<QNode> mynode;
//
//    public MCSLock() {
//        tail = new AtomicReference<MCSLock.QNode>(null);
//        mynode = new ThreadLocal<QNode>() {
//            protected QNode initialValue() {
//                return new QNode();
//            }
//        };
//    }
//
//    @Override
//    public void lock() {
//        QNode node = mynode.get();
//        QNode prenode = tail.getAndSet(node);// CAS原子操作，保证原子性  
//        if (prenode != null) {// 如果preNode等于空，证明是第一个获取锁的  
//            node.lock = true;
//            prenode.next = node;
//            // 对线程自己的node进行自旋，对无cache的NUMA系统架构来说，访问本地内存速度优于其他节点的内存  
//            while (node.lock) {
//
//            }
//        }
//    }
//
//    @Override
//    public void unlock() {
//        QNode node = mynode.get();
//        if (node.next == null) {
//            // CAS操作，判断是否没有新加入的节点  
//            if (tail.compareAndSet(node, null)) {
//                // 没有新加入的节点,直接返回  
//                return;
//            }
//            // 有新加入的节点，等待设置链关系  
//            while (node.next == null) {
//
//            }
//        }
//        // 通知下一个节点获取锁  
//        node.next.lock = false;
//        // 设置next节点为空，为下次获取锁清理状态  
//        node.next = null;
//
//    }
//
//    public static class QNode {
//        volatile boolean lock;
//        volatile QNode next;
//    }
//
//    public String toString() {
//        return "MCSLock";
//    }
//
//}
