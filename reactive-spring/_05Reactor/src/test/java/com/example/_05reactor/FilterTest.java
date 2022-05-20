package com.example._05reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FilterTest {

    @Test
    void filter() {
        var filter = Flux
                .range(0, 1000)
                .take(7)
                .filter(i -> i % 2 == 0);
        StepVerifier.create(filter).expectNext(0, 2, 4, 6).verifyComplete();
    }

}
