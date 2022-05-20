package com.example._05reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class TakeTest {

    @Test
    void take() {
        var n = 10;
        Flux<Integer> take = Flux.range(1, 25).take(n);
        StepVerifier.create(take).expectNextCount(n).verifyComplete();
    }

    @Test
    void takeUntil() {
        var n = 50;
        Flux<Integer> take = Flux.range(0, 2000).takeUntil(i -> i == (n*10 -1));
        StepVerifier.create(take).expectNextCount(500).verifyComplete();
    }

}
