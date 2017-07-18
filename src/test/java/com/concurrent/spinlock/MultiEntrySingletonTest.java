//package com.concurrent.spinlock;
//
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//import java.util.concurrent.atomic.AtomicInteger;
//
//public class MultiEntrySingletonTest {
//
//    // 辅助并发控制的Map，用来找出每个key对应的第一个成功进入的线程  
//    private ConcurrentMap<String, SpinStatus> raceUtil = new ConcurrentHashMap<String, SpinStatus>();
//
//    private ConcurrentMap<String, SingletonObject> map = new ConcurrentHashMap<String, MultiEntrySingletonTest.SingletonObject>();
//
//    public SingletonObject creataeObjectOneTime(String key) {
//        SingletonObject obj = map.get(key);
//        if (obj == null) {
//            System.out.println("Multiple threads concurrent race for key: " + key);
//            // 需要为并发的线程new一个自旋状态，只有第一个成功执行putIfAbsent方法的线程设置的SpinStatus会被共享  
//            SpinStatus spinStatus = new SpinStatus();
//            SpinStatus oldSpinStatus = raceUtil.putIfAbsent(key, spinStatus);
//            // 只有第一个执行成功的线程拿到的oldSpinStatus是null，  
//            //其他线程拿到的oldSpinStatus是第一个线程设置的，可以在所有线程中共享  
//            if (oldSpinStatus == null) {
//                // 创建对象  
//                obj = new SingletonObject(key);
//                // 放入共享的并发Map，后续线程执行get()方法后可以直接拿到非null的引用返回  
//                map.put(key, obj);
//                // 释放其他自旋的线程,注意，对第一个成功执行的线程使用的是spinStatus的引用  
//                spinStatus.released = true;
//            } else {
//                // 其他线程在oldSpinStatus引用所指向的共享自旋状态上自旋，等等被释放  
//                while (!oldSpinStatus.released) {
//                }
//                ;
//            }
//            obj = map.get(key);
//        }
//        return obj;
//    }
//
//    public static void main(String[] args) {
//        final MultiEntrySingletonTest lockFree = new MultiEntrySingletonTest();
//
//        for (int i = 0; i < 10; i++) {
//            Thread t = new Thread(new Runnable() {
//
//                @Override
//                public void run() {
//                    SingletonObject s = lockFree.creataeObjectOneTime("One");
//                    s.printCount();
//                }
//            });
//            t.start();
//        }
//        for (int i = 0; i < 10; i++) {
//            Thread t = new Thread(new Runnable() {
//
//                @Override
//                public void run() {
//                    SingletonObject s2 = lockFree.creataeObjectOneTime("Two");
//                    s2.printCount();
//                }
//            });
//            t.start();
//        }
//    }
//
//    private static class SingletonObject {
//        AtomicInteger count = new AtomicInteger(0);
//
//        String key;
//
//        public SingletonObject(String key) {
//            this.key = key;
//            System.out.println("Object created for key: " + key);
//        }
//
//        public void printCount() {
//            System.out.println("Count " + count.incrementAndGet() + " for key: " + key);
//        }
//    }
//
//    public static class SpinStatus {
//        volatile boolean released;
//    }
//}
