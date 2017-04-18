package org.runafter.ignite.example;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;

/**
 * Created by runaf on 2017-04-18.
 */
public class CacheValue implements Serializable {
    @QuerySqlField(index = true)
    private int firstKey;
    private int secondKey;
    private String value;

    public CacheValue() {
    }
    public CacheValue(int firstKey, int secondKey, String value) {
        this();
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
