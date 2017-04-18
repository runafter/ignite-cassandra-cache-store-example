package org.runafter.ignite.example;

/**
 * Created by runaf on 2017-04-18.
 */
public class Main1 {
    private static final int SIZE = 1000;
    private static final int ITEM_SIZE = 1000;

    public static void main(String[] args) {
//        new org.runafter.ignite.example.CacheValueAsListTestRunner(SIZE, ITEM_SIZE).run();
//        new org.runafter.ignite.example.CacheKeyAsListTestRunner(SIZE, ITEM_SIZE).run();
        new CacheIndexQueryTestRunner(SIZE, ITEM_SIZE).run();
    }



}
