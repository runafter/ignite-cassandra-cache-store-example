package org.runafter.ignite.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by runaf on 2017-04-18.
 */
public class CacheValueAsListTestRunner extends CacheTestRunner<String, List<String>> {
    public CacheValueAsListTestRunner(int size, int itemSize) {
        super("cacheValueAsList", size, itemSize);
    }

    @Override
    protected int find(int k) {
        List<String> list = (List<String>)cache.get(keyOf(k));
        return list == null ? 0 : list.size();
    }

    @Override
    protected void putItems(int i) {
        for (int j = 0 ; j < itemSize ; j++) {
            String key = keyOf(i);
            List<String> v = cache.get(key);
            List<String> list = v == null ? new ArrayList<>() : v;
            list.add("text" + Integer.toString(j));
            cache.put(key, list);
        }
    }
    private static String keyOf(int i) {
        return String.format("key%d", i);
    }
}
