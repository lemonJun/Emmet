package com.takin.emmet.cache;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 *
 * @author WangYazhou
 * @date  2017年3月27日 下午9:37:13
 * @see 
 * @param <K>
 * @param <V>
 */
public class ConcurrentLRUCache<K, V> {

    private int maxCacheSize;
    private final Map<K, CacheItem<V>> cache;
    private final AtomicLong totalEvictCount = new AtomicLong();
    private final AtomicLong hitCount = new AtomicLong();
    private final AtomicLong notHitCount = new AtomicLong();

    public ConcurrentLRUCache(int maxCacheSize) {
        cache = new ConcurrentHashMap<>(maxCacheSize, 1, 10);
        this.maxCacheSize = maxCacheSize;
    }

    public String getStatus() {
        StringBuilder status = new StringBuilder(200);
        long total = hitCount.get() + notHitCount.get();
        status.append("max: ").append(maxCacheSize).append("\n").append("current: ").append(getActualCacheSize());
        status.append("\n").append("expel: ").append(totalEvictCount.get()).append("\n");
        status.append("hit: ").append(hitCount.get()).append("\n");
        status.append("unhit: ").append(notHitCount.get()).append("\n");
        status.append("hitrate: ").append(total == 0 ? 0 : hitCount.get() / (float) total * 100).append(" %\n");
        return status.toString();
    }

    public String getKeyAndHitCount() {
        StringBuilder status = new StringBuilder();
        AtomicInteger i = new AtomicInteger();
        cache.entrySet().stream().sorted((a, b) -> b.getValue().getCount() - a.getValue().getCount()).forEach(entry -> status.append(i.incrementAndGet()).append("\t").append(entry.getKey()).append("\t").append(entry.getValue().getCount()).append("\n"));
        return status.toString();
    }

    public int getMaxCacheSize() {
        return maxCacheSize;
    }

    public int getActualCacheSize() {
        return cache.size();
    }

    public Map<K, CacheItem<V>> getCache() {
        return Collections.unmodifiableMap(cache);
    }

    public AtomicLong getTotalEvictCount() {
        return totalEvictCount;
    }

    public long getHitCount() {
        return hitCount.longValue();
    }

    public long getNotHitCount() {
        return notHitCount.longValue();
    }

    public void put(K key, V value) {
        if (cache.size() >= maxCacheSize) {
            // evict count
            int evictCount = (int) (maxCacheSize * 0.1);
            if (evictCount < 1) {
                evictCount = 1;
            }
            totalEvictCount.addAndGet(evictCount);
            cache.entrySet().stream().sorted((a, b) -> a.getValue().getCount() - b.getValue().getCount()).limit(evictCount).forEach(entry -> cache.remove(entry.getKey()));
            return;
        }
        cache.put(key, new CacheItem<V>(value, new AtomicInteger()));
    }

    public V get(K key) {
        CacheItem<V> item = cache.get(key);
        if (item != null) {
            item.hit();
            hitCount.incrementAndGet();
            return item.getValue();
        }
        notHitCount.incrementAndGet();
        return null;
    }

    private static class CacheItem<V> {
        private V value;
        private AtomicInteger count;

        public CacheItem(V value, AtomicInteger count) {
            this.value = value;
            this.count = count;
        }

        public void hit() {
            this.count.incrementAndGet();
        }

        public V getValue() {
            return value;
        }

        public int getCount() {
            return count.get();
        }
    }

    public static void main(String[] args) {
        ConcurrentLRUCache<Integer, Integer> cache = new ConcurrentLRUCache<>(5);
        for (int i = 0; i < 9; i++) {
            cache.put(i, i);
            if (i % 2 == 0) {
                for (int j = 0; j < i + 2; j++) {
                    cache.get(i);
                }
            }
        }
        System.out.println(cache.getStatus());
        System.out.println(cache.getKeyAndHitCount());
    }
}
