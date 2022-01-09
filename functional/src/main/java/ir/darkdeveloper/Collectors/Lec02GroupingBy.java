package ir.darkdeveloper.Collectors;

import static java.util.stream.Collectors.filtering;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;


public class Lec02GroupingBy {

    public static void main(String[] args) {

        var dishes = new Data().dishes();

       
        // groupingBy
        Map<Type, List<Dish>> dishesByType = dishes.stream().collect(groupingBy(Dish::type));
        System.out.println(dishesByType);

        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = dishes.stream().collect(groupingBy(d -> {
            if (d.calories() <= 400)
                return CaloricLevel.DIET;
            else if (d.calories() <= 700)
                return CaloricLevel.NORMAL;
            else
                return CaloricLevel.FAT;

        }));
        System.out.println(dishesByCaloricLevel);

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

    }

}
