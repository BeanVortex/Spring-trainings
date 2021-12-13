package ir.darkdeveloper.sec03;

import reactor.core.publisher.Flux;

public class Lec02MultipleSubscribers {

    public static void main(String[] args) {
        var integerFlux = Flux.just(1, 2, 3, 4);
        integerFlux.subscribe(d -> System.out.println("Sub 1: " + d));
        integerFlux.subscribe(d -> System.out.println("Sub 2: " + d));

        var evenFlux = integerFlux.filter(d -> d % 2 == 0);
        evenFlux.subscribe(d -> System.out.println("Sub 3: " + d));
    }
}
