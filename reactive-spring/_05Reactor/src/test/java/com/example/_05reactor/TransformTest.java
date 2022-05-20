package com.example._05reactor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.concurrent.atomic.AtomicBoolean;

public class TransformTest {

    @Test
    void transform() {
        var finished = new AtomicBoolean();
        var letters = Flux.just("A", "B", "C");
        var transformedLetters = letters.transform(stringFlux -> stringFlux.map(s -> s + "BV").doFinally(signalType -> finished.set(true)));
        StepVerifier.create(transformedLetters).expectNextCount(3).verifyComplete();
        StepVerifier.create(letters).expectNext("ABV", "BBV", "CBV").verifyComplete();
        Assertions.assertTrue(finished.get(), "the finished bool must be true");

    }
}
