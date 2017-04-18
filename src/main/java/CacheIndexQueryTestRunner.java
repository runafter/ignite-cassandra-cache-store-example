import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.Query;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.cache.query.SqlQuery;
import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.apache.ignite.spi.indexing.IndexingQueryFilter;
import org.jetbrains.annotations.NotNull;

import javax.cache.Cache;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by runaf on 2017-04-18.
 */
public class CacheIndexQueryTestRunner extends CacheTestRunner<String, CacheIndexQueryTestRunner.Value> {
    private SqlQuery<String, Value> sql;

    public CacheIndexQueryTestRunner(int size, int itemSize) {
        super("cacheIndexed", size, itemSize);
    }

    @Override
    protected int find(int k) {
        QueryCursor<Cache.Entry<String, Value>> qc = cache.query(indexQuery(k));

        Iterator<Cache.Entry<String, Value>> iter = qc.iterator();
        List<Integer> list = new ArrayList<>();
        while (iter.hasNext()) {
            list.add(iter.next().getValue().getSecondKey());
        }
        Collections.sort(list);
        return list.size();
    }

    protected void init() {
        ignite = Ignition.start("ignite-cache.xml");
        cache = ignite.cache(cacheName);
        sql = new SqlQuery(Value.class, "firstKey = ?");
    }

    @Override
    protected void putItems(int i) {
        for (int j = 0; j < itemSize; j++) {
            cache.put(keyOf(i, j), new Value(i, j, "text" + j));
        }
    }

    private static String keyOf(int i, int j) {
        return String.format("key%d.%d", i, j);
    }

    @NotNull
    private SqlQuery<String, Value> indexQuery(final int i) {
        return sql.setArgs(i);
    }

    public static class Value implements Serializable {
        @QuerySqlField(index = true)
        private int firstKey;
        private int secondKey;
        private String value;

        public Value(int firstKey, int secondKey, String value) {
            this.firstKey = firstKey;
            this.secondKey = secondKey;
            this.value = value;
        }

        public int getFirstKey() {
            return firstKey;
        }

        public void setFirstKey(int firstKey) {
            this.firstKey = firstKey;
        }

        public int getSecondKey() {
            return secondKey;
        }

        public void setSecondKey(int secondKey) {
            this.secondKey = secondKey;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
