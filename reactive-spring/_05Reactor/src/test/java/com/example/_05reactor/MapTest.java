package com.example._05reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class MapTest {

    @Test
    void map() {
        var letters = Flux.just("a", "b", "c").map(String::toUpperCase);
        StepVerifier.create(letters).expectNext("A", "B", "C").verifyComplete();
    }

    // Interleaves items because of asynchronicity
    @Test
    void flatMap() {
        Flux<Integer> data = Flux
                .just(new Pair(1, 300), new Pair(2, 200), new Pair(3, 100))
                .log()
                .flatMap(pair -> delayReplayFor(pair.id(), pair.delay()))
                .log();

        StepVerifier.create(data)
                .expectNext(3, 2, 1)
                .verifyComplete();
    }

    // Preserves the order of items but loses asynchronicity
    @Test
    void concatMap() {
        Flux<Integer> data = Flux
                .just(new Pair(1, 300), new Pair(2, 200), new Pair(3, 100))
                .log()
                .concatMap(pair -> delayReplayFor(pair.id(), pair.delay()))
                .log();

        StepVerifier.create(data)
                .expectNext(1, 2, 3)
                .verifyComplete();
    }

    // It cancels any outstanding inner publishers as soon as a new value arrives
    @Test
    void switchMap() {
        Flux<String> source = Flux
                .just("re", "rea", "reac", "react", "reactive")
                .delayElements(Duration.ofMillis(100))
                .log()
                .switchMap(this::lookup)
                .log();
        StepVerifier.create(source).expectNext("reactive -> reactive").verifyComplete();
    }

    private Flux<String> lookup(String word) {
        return Flux.just(word + " -> reactive")
                .delayElements(Duration.ofMillis(500));
    }


    private Flux<Integer> delayReplayFor(Integer i, long delay) {
        return Flux.just(i).delayElements(Duration.ofMillis(delay));
    }

    record Pair(int id, long delay) {

    }

}
