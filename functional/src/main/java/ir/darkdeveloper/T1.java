package ir.darkdeveloper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import static java.util.stream.Collectors.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class T1 {

    public static <R> void main(String[] args) {
        // var numbers = new int[] { 1, 2, 5, 6 };
        // var sum = Arrays.stream(numbers).sum();
        // System.out.println(sum);
        // try (Stream<String> lines = Files.lines(Paths.get("src/main/resources/t1.txt"), Charset.defaultCharset())) {
        //     lines.flatMap(line -> Arrays.stream(line.split(" ")))
        //             .distinct()
        //             .forEach(System.out::println);
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        // Stream.iterate(new int[] { 0, 1 }, arr -> {
        //     int cur = arr[0] + arr[1];
        //     int next = cur + arr[1];
        //     return new int[] { cur, next };
        // })
        //         .limit(20)
        //         .flatMapToInt(Arrays::stream)
        //         .boxed()
        //         .collect(Collectors.toList())
        //         .stream()
        //         .forEach(i -> System.out.print(i + " "));

        // java 9 enhanced version with support of predicate 
        // Stream.iterate(0, n -> n<10, n -> n+1)
        // .forEach(System.out::print);

        // Stream.generate(Math::random)
        // .limit(2)
        // .forEach(System.out::println);

        // var fib = new IntSupplier() {

        //     private int pre = 0;
        //     private int cur = 1;

        //     @Override
        //     public int getAsInt() {
        //         int oldPre = this.pre;
        //         int next = this.pre + this.cur;
        //         this.pre = this.cur;
        //         this.cur = next;
        //         return oldPre;
        //     }
        // };

        // // generate and iterate creates infinite streams so it should be reduced or limited
        // IntStream.generate(fib).limit(10).forEach(System.out::println);

        var list = List.of("ab", "b", "c");
        System.out.println(list.stream().collect(counting()));
        System.out.println(list.stream().count());
        System.out.println(list.stream().collect(maxBy(Comparator.comparing(String::length))));
    }

}
