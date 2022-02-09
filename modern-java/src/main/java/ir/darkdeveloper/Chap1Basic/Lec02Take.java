package ir.darkdeveloper.Chap1Basic;

import java.util.List;
import static java.util.stream.Collectors.*;

public class Lec02Take {

    public static void main(String[] args) {
        var numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // if condition returns false, it will stop
        List<Integer> tookIntegers = numbers.stream()
                .takeWhile(num -> num <= 5)
                .collect(toList());
        System.out.println(tookIntegers);

        // when the condition returns false, the rest of list will be considered
        List<Integer> droppedIntegers = numbers.stream()
                .dropWhile(num -> num <= 5)
                .collect(toList());
        System.out.println(droppedIntegers);
    }

}
