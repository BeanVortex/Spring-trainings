package ir.darkdeveloper.Collectors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import static java.util.stream.Collectors.*;
import java.util.stream.IntStream;

public class Lec05Assignment {

    public static void main(String[] args) {

        // typical partitioning test
        var fastest1 = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            var start = System.nanoTime();
            partitionPrimeNumbers(1_000_000);
            var duration = (System.nanoTime() - start) / 1_000_000;
            if (duration < fastest1)
                fastest1 = duration;
        }
        System.out.println("Fastest execution with partitioningBy done in " + (fastest1) + " ms");

        // custom partitioning collector test
        var fastest2 = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            var start = System.nanoTime();
            partitionPrimesWithCustomCollector(1_000_000);
            var duration = (System.nanoTime() - start) / 1_000_000;
            if (duration < fastest1)
                fastest2 = duration;
        }
        System.out.println("Fastest execution with custom collector done in " + (fastest2) + " ms");

    }

    public static Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector(int n) {
        return IntStream.rangeClosed(2, n)
                .boxed()
                .collect(new PrimeNumbersCollector());
    }

    private static boolean isPrime(int num) {
        var square = (int) Math.sqrt((double) num);
        return IntStream.range(2, square)
                .noneMatch(i -> num % i == 0);
    }

    private static Map<Boolean, List<Integer>> partitionPrimeNumbers(int n) {
        return IntStream.rangeClosed(2, n)
                .boxed()
                .collect(partitioningBy(Lec05Assignment::isPrime));
    }

}

class PrimeNumbersCollector implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {

    @Override
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
        return (map, num) -> map.get(isPrime(num)).add(num);
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
    }

    @Override
    public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
        return (map1, map2) -> {
            map1.get(true).addAll(map2.get(true));
            map1.get(false).addAll(map2.get(false));
            return map1;
        };
    }

    @Override
    public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
        return Function.identity();
    }

    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
        return () -> Map.ofEntries(
                Map.entry(false, new ArrayList<>()),
                Map.entry(true, new ArrayList<>()));
    }

    private boolean isPrime(int num) {
        var square = (int) Math.sqrt((double) num);
        return IntStream.range(2, square)
                .noneMatch(i -> num % i == 0);
    }

}
