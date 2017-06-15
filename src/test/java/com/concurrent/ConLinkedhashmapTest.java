package com.concurrent;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import com.takin.emmet.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.takin.emmet.concurrentlinkedhashmap.EvictionListener;
import com.takin.emmet.concurrentlinkedhashmap.Weighers;

public class ConLinkedhashmapTest {

    public static void main(String[] args) {

        test001();
        //        test002();

    }

    private static void test001() {
        EvictionListener<String, String> listener = new EvictionListener<String, String>() {
            @Override
            public void onEviction(String key, String value) {
                System.out.println("Evicted key=" + key + ", value=" + value);
            }
        };
        ConcurrentMap<String, String> cache = new ConcurrentLinkedHashMap.Builder<String, String>()//
                        .maximumWeightedCapacity(10).listener(listener).build();

        for (int i = 0; i < 150; i++) {
            int j = 1024;
            j = j + i;
            cache.put(String.valueOf(j), "nihao" + i);
        }

        for (Map.Entry<String, String> entry : cache.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key + "====" + value);
        }
        System.out.println(cache.get("1025"));
        cache.remove("1026");

    }

    /** 
    ConcurrentLinkedHashMap 是google团队提供的一个容器。它有什么用呢？其实它本身是对 
    ConcurrentHashMap的封装，可以用来实现一个基于LRU策略的缓存。详细介绍可以参见   
    http://code.google.com/p/concurrentlinkedhashmap 
    */
    private static void test002() {

        ConcurrentLinkedHashMap<Integer, Integer> map = new ConcurrentLinkedHashMap.Builder<Integer, Integer>().maximumWeightedCapacity(2).weigher(Weighers.singleton()).build();

        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        System.out.println(map.get(1));// null 已经失效了  
        System.out.println(map.get(2));
    }
}
