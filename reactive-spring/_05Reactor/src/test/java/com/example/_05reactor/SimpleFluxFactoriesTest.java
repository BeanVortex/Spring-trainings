package com.example._05reactor;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class SimpleFluxFactoriesTest {

    @Test
    void simple() {
        Publisher<Integer> rangeOfIntegers = Flux.range(0, 16);
        StepVerifier.create(rangeOfIntegers).expectNextCount(16).verifyComplete();

        Flux<String> letters = Flux.just("A", "B", "C");
        StepVerifier.create(letters).expectNext("A", "B", "C").verifyComplete();

        long now = System.currentTimeMillis();
        Mono<Date> date = Mono.just(new Date(now));
        StepVerifier.create(date).expectNext(new Date(now)).verifyComplete();

        Mono<Object> empty = Mono.empty();
        StepVerifier.create(empty).verifyComplete();

        Flux<Integer> fromIterable = Flux.fromIterable(List.of(1, 2, 3));
        StepVerifier.create(fromIterable).expectNext(1, 2, 3).verifyComplete();

        Flux<Integer> fromArray = Flux.fromArray(new Integer[]{1, 2, 3});
        StepVerifier.create(fromArray).expectNext(1, 2, 3).verifyComplete();

        AtomicInteger atomicInteger = new AtomicInteger();
        Supplier<Integer> supplier = atomicInteger::incrementAndGet;
        Flux<Integer> integerFlux = Flux.fromStream(Stream.generate(supplier));
        StepVerifier.create(integerFlux.take(4)).expectNext(1, 2, 3, 4).verifyComplete();
    }
}
