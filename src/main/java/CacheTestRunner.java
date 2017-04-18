import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by runaf on 2017-04-18.
 */
public abstract class CacheTestRunner<K, V> {

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
    protected void init() {
        ignite = Ignition.start("ignite-cache.xml");
        cache = ignite.getOrCreateCache(cacheName);
    }

    private void close() {
        ignite.close();
    }
    public void run() {
        init();
        System.out.println("start " + cacheName);
        long start = System.currentTimeMillis(), pe;
        for (int i = 0 ; i < size ; i++) {
            putItems(i);
            System.out.println("cache put(" + i + ") " + (System.currentTimeMillis() - start) + " ms");
        }
        pe = System.currentTimeMillis() - start;

        Integer pool[] = queryKeyPool();
        long s, fe;
        int found;
        start = System.currentTimeMillis();
        for (Integer k : pool) {
            s = System.currentTimeMillis();
            found = find(k);
            System.out.println("finding " + found + " tooks " + (System.currentTimeMillis() - s) + " ms");
        }
        fe = System.currentTimeMillis() - start;

        System.out.println("end " + cacheName + " put: " + pe + " ms , get: " + fe + " ms \n");

        close();
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
