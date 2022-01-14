package ir.darkdeveloper.Chap04Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Lec01CollectionFactories {

    public static void main(String[] args) {

        var numbers = Arrays.asList(1, 2, 3, 4, 5);
        numbers.set(0, -1);
        assertThrows(UnsupportedOperationException.class, () -> numbers.add(6));
        System.out.println(numbers);

        // only 10 methods overloaded like this 
        // after then arguments, it accepts varargs
        // under 10 elements it reduces memory usage by not creating and array
        var immutableNumbers = List.of(1, 2, 3, 4, 5);
        assertThrows(UnsupportedOperationException.class, () -> {
            immutableNumbers.set(0, 2);
            immutableNumbers.add(6);
        });
        System.out.println(immutableNumbers);

        // throws IllegalArgumentException for duplicated elements
        Set.of(1, 2, 3);
        assertThrows(IllegalArgumentException.class, () -> Set.of(1, 1, 2));

        var map = Map.of(1, "one", 2, "two");
        System.out.println(map);
        // for more than 10 key value pairs use ofEntries
        var mapEntrySet = Map.ofEntries(
                Map.entry(1, "yek"),
                Map.entry(2, "do"),
                Map.entry(3, "se"));
        System.out.println(mapEntrySet);
    }
}