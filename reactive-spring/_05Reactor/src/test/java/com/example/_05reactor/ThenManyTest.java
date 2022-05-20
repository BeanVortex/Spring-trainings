package com.example._05reactor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.atomic.AtomicInteger;

public class ThenManyTest {


    @Test
    void thenMany() {
        var letters = new AtomicInteger();
        var numbers = new AtomicInteger();

        Flux<String> lettersPublisher = Flux.just("a", "b", "c")
                .doOnNext(val -> letters.incrementAndGet());
        Flux<Integer> numbersPublisher = Flux.just(1, 2, 3)
                .doOnNext(val -> numbers.incrementAndGet());

        // goes through the all elements of first publisher (lettersPublisher)
        // and then goes through the all elements of the publisher which is passed in the parameter
        var integerFlux = lettersPublisher.thenMany(numbersPublisher);

        StepVerifier.create(integerFlux).expectNext(1, 2, 3).verifyComplete();
        Assertions.assertEquals(letters.get(), 3);
        Assertions.assertEquals(numbers.get(), 3);

    }

    @Test
    void then() {
        var letters = new AtomicInteger();
        var numbers = new AtomicInteger();

        Mono<String> letterPublisher = Mono.just("a")
                .doOnNext(val -> letters.incrementAndGet());
        Mono<Integer> numberPublisher = Mono.just(1)
                .doOnNext(val -> numbers.incrementAndGet());

        // goes through the element of first publisher (lettersPublisher)
        // and then goes through the element of the publisher which is passed in the parameter
        var integerFlux = letterPublisher.then(numberPublisher);

        StepVerifier.create(integerFlux).expectNext(1).verifyComplete();
        Assertions.assertEquals(letters.get(), 1);
        Assertions.assertEquals(numbers.get(), 1);

    }
}
