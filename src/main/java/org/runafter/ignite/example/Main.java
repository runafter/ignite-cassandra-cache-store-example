package org.runafter.ignite.example;


/**
 * Created by runaf on 2017-04-18.
 */
public class Main  {

    public static void main(String[] args) {
        CacheTestRunner testRunner = testRunnerOf(args);

        testRunner.init();
        for (int i = 0 ; i < 10 ; i++)
            testRunner.run();
        testRunner.close();
        System.exit(0);
    }

    private static CacheTestRunner testRunnerOf(String[] args) {
        int size = sizeOf(args);
        int itemSize = itemSizeOf(args);
        switch(optionOf(args)) {
            case 0:
                return new CacheIndexQueryTestRunner("cacheCassandraIndexedReadWriteThroughBehind5000", size, itemSize);
            case 1:
                return new CacheIndexQueryTestRunner("cacheCassandraIndexedReadWriteThroughBehind1000", size, itemSize);
            case 2:
                return new CacheIndexQueryTestRunner("cacheCassandraIndexedReadWriteThrough", size, itemSize);
            case 3:
                return new CacheIndexQueryTestRunner("cacheCassandraIndexedReadThrough", size, itemSize);
            case 4:
                return new CacheValueAsListTestRunner("cacheCassandraReadWriteThroughBehind5000", size, itemSize);
            case 5:
                return new CacheValueAsListTestRunner("cacheCassandraReadWriteThroughBehind1000", size, itemSize);
            case 6:
                return new CacheValueAsListTestRunner("cacheCassandraReadWriteThrough", size, itemSize);
            case 7:
                return new CacheValueAsListTestRunner("cacheCassandraReadThrough", size, itemSize);
            default:
                return new CacheIndexQueryTestRunner("cacheCassandraReadWriteThroughBehind5000", size, itemSize);
        }
//        CacheTestRunner testRunners[] = {
//
////                ,new CacheIndexQueryTestRunner("cacheCassandraReadWriteThroughBehind1000", SIZE, ITEM_SIZE)
////                ,new CacheIndexQueryTestRunner("cacheCassandraReadWriteThrough", SIZE, ITEM_SIZE)
////                ,new CacheIndexQueryTestRunner("cacheCassandraReadThrough", SIZE, ITEM_SIZE)
////                ,new CacheValueAsListTestRunner(SIZE, ITEM_SIZE)
////                ,new CacheKeyAsListTestRunner(SIZE, ITEM_SIZE)
//        };
    }

    private static int itemSizeOf(String[] args) {
        return args.length < 2 ? 5 : toInt(args[1]);
    }

    private static int sizeOf(String[] args) {
        return args.length < 3 ? 5 : toInt(args[2]);
    }

    private static int toInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch(NumberFormatException e) {
            return 5;
        }
    }

    private static int optionOf(String[] args) {
        try {
            return args.length == 0 ? 0 : Integer.parseInt(args[0]);
        } catch (Throwable t) {
            return 0;
        }
    }


}

