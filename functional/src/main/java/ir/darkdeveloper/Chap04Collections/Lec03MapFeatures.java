package ir.darkdeveloper.Chap04Collections;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class Lec03MapFeatures {

    private static Map<Integer, String> map = Map.of(1, "one", 2, "two", 3, "three", 4, "four");

    public static void main(String[] args) {

        iterateMapTraditional();
        iterateMapForeach();
        sortingMap();
        getOrDefaultMap();
        computeIfAbsent();
        removeWithCheckValTraditional();
        removeWithCheckValNew();
        replaceAll();
        merge();
        removeIf();

    }

    private static void iterateMapTraditional() {
        System.out.println("\niterateMapTraditional");
        for (Map.Entry<Integer, String> entry : map.entrySet())
            System.out.printf("%d = %s%n", entry.getKey(), entry.getValue());
    }

    private static void iterateMapForeach() {
        System.out.println("\niterateMapForeach");
        // forEach accepts a BiConsumer first arg is key second in value
        map.forEach((key, val) -> System.out.printf("%d = %s%n", key, val));
    }

    private static void sortingMap() {
        System.out.println("\nsortingMap");
        /* forEach in stream:  The behavior of this operation is explicitly nondeterministic. 
         For parallel stream pipelines, this operation does not guarantee to respect the encounter order of the stream,
         as doing so would sacrifice the benefit of parallelism.
        */

        /* forEachOrdered in stream: Performs an action for each element of this stream, in the encounter order of 
         the stream if the stream has a defined encounter order.
        */

        map.entrySet().stream().parallel()
                .sorted(Entry.comparingByValue())
                .forEachOrdered(System.out::println);
    }

    private static void getOrDefaultMap() {
        System.out.println("\ngetOrDefaultMap");
        // if key was found, default val will be ignored
        // if key not found, default val will be considered
        // if key was found and value of that key was null, it returns null
        System.out.println(map.getOrDefault(0, "zero"));
    }

    private static void computeIfAbsent() {
        System.out.println("\ncomputeIfAbsent");

        Map<Integer, List<String>> m = new HashMap<>();
        m.put(0, List.of("0", "zero"));
        m.computeIfAbsent(1, list -> List.of("1", "one"));
        System.out.println(m);
    }

    private static boolean removeWithCheckValTraditional() {
        System.out.println("\nremoveWithCheckValTraditional");
        var map = new HashMap<Integer, String>();
        map.put(1, "one");
        map.put(2, "two");
        System.out.println(map);
        if (map.containsKey(1)
                && Objects.equals(map.get(1), "one")) {
            map.remove(1);
            System.out.println(map);
            return true;
        }
        System.out.println(map);
        return false;
    }

    private static void removeWithCheckValNew() {
        System.out.println("\nremoveWithCheckValNew");
        var map = new HashMap<Integer, String>();
        map.put(1, "one");
        map.put(2, "two");
        System.out.println(map);
        map.remove(1, "one");
        System.out.println(map);
    }

    private static void replaceAll() {
        System.out.println("\nreplaceAll");
        var map = new HashMap<String, Integer>();
        map.put("one", 1);
        map.put("two", 2);
        // BiFunction
        // works exactly like list replaceAll
        map.replaceAll((key, val) -> val * 2);
        System.out.println(map);
    }

    private static void merge() {
        System.out.println("\nmerge");

        var family = Map.of("Teo", "Star Wars", "Cristina", "James Bond");
        var friends = Map.of("Raphael", "Star Wars", "Cristina", "Matrix");
        var everyone = new HashMap<>(family);
        // cristina's james bond is lost
        everyone.putAll(friends);
        System.out.println(everyone);

        // handling key conflict
        var everyone2 = new HashMap<>(family);
        friends.forEach((k, v) -> everyone2.merge(k, v, (movie1, movie2) -> movie1 + " & " + movie2));
        System.out.println(everyone2);

    }

    private static void removeIf() {
        System.out.println("\nremoveIf");
        var map = new HashMap<String, Integer>();
        map.put("Inception", 23);
        map.put("Platform", 44);
        map.put("Tenet", -40);

        map.entrySet().removeIf(e -> e.getValue() < 0);
        System.out.println(map);
    }

}
