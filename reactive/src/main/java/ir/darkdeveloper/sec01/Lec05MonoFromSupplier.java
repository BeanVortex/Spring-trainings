package ir.darkdeveloper.sec01;

import java.util.concurrent.Callable;

import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.Mono;

public class Lec05MonoFromSupplier {

    public static void main(String[] args) {

        // use just when you have data already 
        // Mono<String> mono = Mono.just(getName());

        Mono<String> mono = Mono.fromSupplier(() -> getName());
        mono.subscribe(Util.onNext());

        Callable<String> callable = () -> getName();
        Mono<String> mono2 = Mono.fromCallable(callable);
        mono2.subscribe(Util.onNext());
    }

    private static String getName() {
        System.out.println("Generating name ...");
        return Util.faker().name().fullName();
    }

}
