package ir.darkdeveloper.Chap04Collections;

import java.util.concurrent.ConcurrentHashMap;

public class Lec04ConcurrentHashMap {

    public static void main(String[] args) {
        // Thread safe implementations for Map interface
        foreach();
        reduce();
        // this new implementation of concurrent hash map has
        // another new methods:
        /*
         * forEach
         * reduce
         * search
         * forEachKey
         * reduceKeys
         * searchKeys
         * forEachValue
         * reduceValues
         * searchValues
         * forEachEntry
         * reduceEntries
         * searchEntries
         * mappingCount // returns the number of mappings in the map as a long
         *  You should use it for new code in preference to the size method, which returns an int.
         *  Doing so future proofs your code for use when the number of mappings no longer fits in an int.
         * */
    }

    private static void foreach() {
        System.out.println("\nForeach");
        var map = new ConcurrentHashMap<String, Integer>();
        map.put("null", 1);
        map.put("null2", 2);
        map.put("null3", 3);
        map.forEach((k, v) -> System.out.println(k + " " + v));
        System.out.println("\nForeach Key");
        map.forEachKey(0l, k -> System.out.println(k + " "));
    }

    private static void reduce() {
        System.out.println("\nreduce");
        var map = new ConcurrentHashMap<String, Integer>();
        map.put("null", 1);
        map.put("null2", 2);
        map.put("null3", 3);
        var reducedKeys = map.reduceKeys(1, (k1, k2) -> k1 + k2);
        System.out.println(reducedKeys);
        System.out.println(map.mappingCount());
    }
}
