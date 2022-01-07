package ir.darkdeveloper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.ToString;

import static java.util.stream.Collectors.*;

import java.sql.Array;

import static java.util.Comparator.*;

public class T2StreamCollectors {

    public static void main(String[] args) {

        var dishes = List.of(
                new Dish("beef", 600, Type.MEAT),
                new Dish("vegetables dish", 150, Type.VEGETABLE),
                new Dish("cow meat", 1200, Type.MEAT),
                new Dish("kebab", 2000, Type.MEAT),
                new Dish("salmon", 2530, Type.FISH),
                new Dish("corn", 2530, Type.OTHERS),
                new Dish("lamb", 2530, Type.LAMB),
                new Dish("lamb", 400, Type.LAMB));

        // summing and reducing
        // System.out.println(dishes.stream().collect(counting()));
        // System.out.println(dishes.stream().count());
        // System.out.println(dishes.stream().collect(maxBy(comparing(Dish::calories))));
        // System.out.println(dishes.stream().collect(summingInt(Dish::calories)));
        // System.out.println(dishes.stream().collect(averagingInt(Dish::calories)));
        // System.out.println(dishes.stream().collect(summarizingInt(Dish::calories)));

        // // joining returns toString method of and obj
        // // joining uses StringBuilder internally
        // System.out.println(dishes.stream().map(Dish::name).collect(joining()));
        // // System.out.println(dishes.stream().collect(joining()));
        // System.out.println(dishes.stream().map(Dish::name).collect(joining(", ")));

        // // reducing
        // System.out.println(dishes.stream().collect(reducing(0, Dish::calories, (i, j) -> i + j)));
        // System.out.println(
        //         "Max calories dish: "
        //                 + dishes.stream().collect(reducing((d1, d2) -> d1.calories() > d2.calories() ? d1 : d2)));

        // // groupingBy
        // Map<Type, List<Dish>> dishesByType = dishes.stream().collect(groupingBy(Dish::type));
        // System.out.println(dishesByType);

        // Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = dishes.stream().collect(groupingBy(d -> {
        //     if (d.calories() <= 400)
        //         return CaloricLevel.DIET;
        //     else if (d.calories() <= 700)
        //         return CaloricLevel.NORMAL;
        //     else
        //         return CaloricLevel.FAT;

        // }));
        // System.out.println(dishesByCaloricLevel);

        // does not include other types even empty
        Map<Type, List<Dish>> dishesByTypeFiltered = dishes.stream().filter(dish -> dish.calories() > 500)
                .collect(groupingBy(Dish::type));
        System.out.println(dishesByTypeFiltered);

        // does include other types even empty
        Map<Type, List<Dish>> dishesByTypeFilteredIncluding = dishes.stream()
                .collect(groupingBy(Dish::type, filtering(dish -> dish.calories() >= 500, toList())));
        System.out.println(dishesByTypeFilteredIncluding);

        Map<Type, List<String>> dishesByTypeMapping = dishes.stream()
                .collect(groupingBy(Dish::type, mapping(Dish::name, toList())));
        System.out.println(dishesByTypeMapping);

        // composing groupings
        

    }

}

enum CaloricLevel {
    DIET, NORMAL, FAT
}

enum Type {
    FISH,
    VEGETABLE,
    LAMB,
    MEAT,
    OTHERS
}

@ToString(of = { "name" })
record Dish(String name, int calories, Type type) {

}