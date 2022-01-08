package ir.darkdeveloper.Collectors;

import java.util.List;

public class Data {
    private List<Dish> dishes;

    public Data() {
        dishes = List.of(
                new Dish("beef", 600, Type.MEAT),
                new Dish("vegetables dish", 150, Type.VEGETABLE),
                new Dish("cow meat", 1200, Type.MEAT),
                new Dish("kebab", 2000, Type.MEAT),
                new Dish("salmon", 2530, Type.FISH),
                new Dish("corn", 2530, Type.OTHERS),
                new Dish("lamb", 2530, Type.LAMB),
                new Dish("lamb", 400, Type.LAMB));
    }

    public List<Dish> dishes() {
        return dishes;
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

record Dish(String name, int calories, Type type) {

    @Override
    public String toString() {
        return name;
    }

}