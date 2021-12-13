package ir.darkdeveloper.sec03;

import java.util.Arrays;

import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.Flux;

public class Lec03FluxFromArrayOrList {

    public static void main(String[] args) {

        var strings = Arrays.asList("a", "c", "v");

        Flux.fromIterable(strings)
                .subscribe(Util.onNext());

        var ints = new Integer[] { 1, 2, 3 };

        Flux.fromArray(ints)
                .subscribe(Util.onNext());
    }
}
