package ir.darkdeveloper.sec05;

import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec06OnError {

    public static void main(String[] args) {

        Flux.range(1, 10)
                .log()
                .map(i -> 10 / (5 - i))
                // .onErrorReturn(-1) // stops the whole data flow and returns the value
                // .onErrorResume(e -> fallback())
                .onErrorContinue((err, item) -> System.out.println(err.getMessage() + " : on Item " + item))
                .subscribe(Util.subscriber());

    }

    private static Mono<Integer> fallback() {
        return Mono.fromSupplier(() -> Util.faker().random().nextInt(100, 150));
    }

}
