package org.runafter.ignite.example;

import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.jetbrains.annotations.NotNull;

import javax.cache.Cache;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by runaf on 2017-04-18.
 */
public class CacheKeyAsListTestRunner extends CacheTestRunner<String, String> {
    public CacheKeyAsListTestRunner(int size, int itemSize) {
        super("cacheKeyAsList", size, itemSize);
        }

        @Override
        protected int find(int k) {
            QueryCursor<Cache.Entry<String, String>> qc = cache.query(scanQuery(String.format("key%d.", k)));
            Iterator<Cache.Entry<String, String>> iter = qc.iterator();
            List<String> list = new ArrayList<>();
            while (iter.hasNext()) {
                Cache.Entry<String, String> e = iter.next();
                list.add(e.getValue());
        }
//        Collections.sort(list);
        return list.size();
    }

    @Override
    protected void putItems(int i) {
        for (int j = 0 ; j < itemSize ; j++) {
            cache.put(keyOf(i, j), "text" + Integer.toString(j));
        }
    }
    private static String keyOf(int i, int j) {
        return String.format("key%d.%d", i, j);
    }

    @NotNull
    private static ScanQuery<String, String> scanQuery(final String prefix) {
        return new ScanQuery<>(new IgniteBiPredicate<String, String>(){
            @Override
            public boolean apply(String s, String o) {
                return s.startsWith(prefix);
            }
        });
    }
}
