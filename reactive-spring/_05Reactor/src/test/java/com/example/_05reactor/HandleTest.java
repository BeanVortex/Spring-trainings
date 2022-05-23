package com.example._05reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.stream.Stream;

public class HandleTest {

    @Test
    void handle() {

        StepVerifier.create(this.handle(5, 4))
                .expectNext(0, 1, 2, 3)
                .expectError(IllegalArgumentException.class)
                .verify();

        StepVerifier.create(this.handle(3, 3))
                .expectNext(0, 1, 2)
                .verifyComplete();

    }

    // use handle when we need to do lots of things with elements.
    // it gives us more control on data
    Flux<Integer> handle(int max, int numberToError) {
        return Flux
                .range(0, max)
                .handle((val, sink) -> {
                    var upTo = Stream.iterate(0, i -> i < numberToError, i -> i + 1)
                            .toList();
                    if (upTo.contains(val)) {
                        sink.next(val);
                        return;
                    }
                    if (val == numberToError) {
                        sink.error(new IllegalArgumentException("No 4 for u"));
                        return;
                    }
                    sink.complete();
                });
    }

}
