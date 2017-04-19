package org.runafter.ignite.example;

import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlQuery;
import org.jetbrains.annotations.NotNull;

import javax.cache.Cache;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by runaf on 2017-04-18.
 */
public class CacheIndexQueryTestRunner extends CacheTestRunner<String, CacheValue> {
    private SqlQuery<String, CacheValue> sql;

    public CacheIndexQueryTestRunner(String cacheName, int size, int itemSize) {
        super(cacheName == null? "cacheCassandraReadWriteThrough" : cacheName, size, itemSize);
    }

    @Override
    protected int find(int k) {
        QueryCursor<Cache.Entry<String, CacheValue>> qc = cache.query(indexQuery(k));

        Iterator<Cache.Entry<String, CacheValue>> iter = qc.iterator();
        List<Integer> list = new ArrayList<>();
        while (iter.hasNext()) {
            list.add(iter.next().getValue().getSecondKey());
        }
        Collections.sort(list);
        return list.size();
    }

    public void init() {
        ignite = Ignition.start("ignite-cache.xml");
        cache = ignite.cache(cacheName);
        sql = new SqlQuery<>(CacheValue.class, "firstKey = ?");
    }

    @Override
    protected void putItems(int i) {
        for (int j = 0; j < itemSize; j++) {
            cache.put(keyOf(i, j), new CacheValue(i, j, "text" + j));
        }
    }

    private static String keyOf(int i, int j) {
        return String.format("key%d.%d", i, j);
    }

    @NotNull
    private SqlQuery<String, CacheValue> indexQuery(final int i) {
        return sql.setArgs(i);
    }

}
