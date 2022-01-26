package ir.darkdeveloper.Cha05Logging;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Lec01Logging {
    public static void main(String[] args) {
        IntStream.range(0, 10)
                .peek(i -> System.out.println("before map to obj: " + i))
                .mapToObj(i -> i*2)
                .peek(i -> System.out.println("after map to obj: " + i))
                .peek(i -> System.out.println("before filter: " + i))
                .filter(i -> i % 2 == 0)
                .peek(i -> System.out.println("after filter: " + i))
                .collect(Collectors.toList());
    }
}
