package ir.darkdeveloper.Chap2Collectors;

import static java.util.stream.Collectors.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Comparator.*;

public class Lec03ComposingGroupings {

    public static void main(String[] args) {

        var dishes = new Data().dishes();

        
        // composing groupings
        
        int size = dishes.stream().collect(collectingAndThen(toList(), List::size));
        System.out.println(size);
        
        // for second argument of groupingBy we can use another collectors
        Map<Type, Map<CaloricLevel, List<Dish>>> dishesByTypeAndMaxCalory = dishes.stream()
                .collect(groupingBy(Dish::type,
                        groupingBy(dish -> {
                            if (dish.calories() <= 400)
                                return CaloricLevel.DIET;
                            else if (dish.calories() <= 700)
                                return CaloricLevel.NORMAL;
                            else
                                return CaloricLevel.FAT;
                        })));
        System.out.println(dishesByTypeAndMaxCalory);

        Map<Type, Long> dishesByTypeCounting = dishes.stream()
                .collect(groupingBy(Dish::type, counting()));
        System.out.println(dishesByTypeCounting);

        Map<Type, Dish> mostCaloricByType = dishes.stream()
                .collect(groupingBy(Dish::type,
                        collectingAndThen(
                                maxBy(comparingInt(Dish::calories)),
                                Optional::get)));
        System.out.println(mostCaloricByType);

        Map<Type, String> joinedDishesName = dishes.stream()
                .collect(groupingBy(Dish::type,
                        mapping(Dish::name, joining(", "))));
        System.out.println(joinedDishesName);

        Map<Type, Double> averageDishesTypeCalories = dishes.stream()
                .collect(groupingBy(Dish::type,
                        averagingInt(Dish::calories)));
        System.out.println(averageDishesTypeCalories);

        Map<Type, HashSet<CaloricLevel>> caloricLevelsByType = dishes.stream()
                .collect(groupingBy(Dish::type,
                        mapping(dish -> {
                            if (dish.calories() <= 400)
                                return CaloricLevel.DIET;
                            else if (dish.calories() <= 700)
                                return CaloricLevel.NORMAL;
                            else
                                return CaloricLevel.FAT;
                        }, toCollection(HashSet::new))));
        System.out.println(caloricLevelsByType);
    }

}
