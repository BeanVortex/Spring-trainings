package ir.darkdeveloper.Collectors;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class Lec04Partitioning {

    public static void main(String[] args) {
        var dishes = new Data().dishes();

        Map<Boolean, List<Dish>> partitionedDishes = dishes.stream()
                .collect(partitioningBy(Dish::isVegetarian));
        System.out.println(partitionedDishes);
        //similar to filter method
        System.out.println(dishes.stream().filter(Dish::isVegetarian).collect(toList()));

        // can use other collectors in second arg
        Map<Boolean, Map<Type, List<Dish>>> partitionedDishesWithTypes = dishes.stream()
                .collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::type)));
        System.out.println(partitionedDishesWithTypes);

        // partitioned prime and non prime numbers till n
        System.out.println(partitionPrimeNumbers(100));

    }

    private static boolean isPrime(int num) {
        int numRooted = (int) Math.sqrt((double) num);
        return IntStream.range(2, numRooted)
                .noneMatch(i -> num % i == 0);
    }

    private static Map<Boolean, List<Integer>> partitionPrimeNumbers(int n) {
        return IntStream.rangeClosed(2, n)
                .boxed()
                .collect(partitioningBy(i -> isPrime(i)));
    }

}
