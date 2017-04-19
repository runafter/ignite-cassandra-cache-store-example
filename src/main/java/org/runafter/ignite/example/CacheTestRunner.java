package org.runafter.ignite.example;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by runaf on 2017-04-18.
 */
public abstract class CacheTestRunner<K, V> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected final String cacheName;
    protected final int size;
    protected final int itemSize;
    protected IgniteCache<K, V> cache;
    protected Ignite ignite;

    public CacheTestRunner(String cacheName, int size, int itemSize) {
        this.cacheName = cacheName;
        this.size = size;
        this.itemSize = itemSize;
    }
    public void init() {
        ignite = Ignition.start("ignite-cache.xml");
        cache = ignite.getOrCreateCache(cacheName);
    }

    public void close() {
        ignite.close();
    }
    public void run() {
        logger.debug("start {}", cacheName);
        long start = System.currentTimeMillis(), pe;
        for (int i = 0 ; i < size ; i++) {
            putItems(i);
            if (logger.isDebugEnabled())
                logger.debug("cache put({}) {} ms", i, (System.currentTimeMillis() - start));
        }
        pe = System.currentTimeMillis() - start;

        Integer pool[] = queryKeyPool();
        long s, fe;
        int found;
        start = System.currentTimeMillis();
        for (Integer k : pool) {
            found = find(k);
            if (logger.isDebugEnabled())
                logger.debug("cache find({}) {} ms : {}", k, (System.currentTimeMillis() - start), found);
        }
        fe = System.currentTimeMillis() - start;

        logger.info("{} put: {} ms , get: {} ms", cacheName, pe, fe);
    }

    protected abstract int find(int k);

    private Integer[] queryKeyPool() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0 ; i < size ; i++)
            list.add(i);
        Collections.shuffle(list);
        return list.toArray(new Integer[list.size()]);
    }

    protected abstract void putItems(int i);
}
