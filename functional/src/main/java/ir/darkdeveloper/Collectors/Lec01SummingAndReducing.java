package ir.darkdeveloper.Collectors;

import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

public class Lec01SummingAndReducing {
    public static void main(String[] args) {

        var dishes = new Data().dishes();

        System.out.println(dishes.stream().collect(counting()));
        System.out.println(dishes.stream().count());
        System.out.println(dishes.stream().collect(maxBy(comparing(Dish::calories))));
        System.out.println(dishes.stream().collect(summingInt(Dish::calories)));
        System.out.println(dishes.stream().collect(averagingInt(Dish::calories)));
        System.out.println(dishes.stream().collect(summarizingInt(Dish::calories)));

        // joining returns toString method of and obj
        // joining uses StringBuilder internally
        System.out.println(dishes.stream().map(Dish::name).collect(joining()));
        // System.out.println(dishes.stream().collect(joining()));
        System.out.println(dishes.stream().map(Dish::name).collect(joining(", ")));

        // reducing
        System.out.println(dishes.stream().collect(reducing(0, Dish::calories, (i, j) -> i + j)));
        System.out.println(
                "Max calories dish: "
                        + dishes.stream().collect(reducing((d1, d2) -> d1.calories() > d2.calories() ? d1 : d2)));

    }
}
