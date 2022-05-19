package com.example._05reactor;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.test.StepVerifier;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class AsyncApiIntegrationTest {

    private final ExecutorService service = Executors.newFixedThreadPool(10);

    @Test
    void async() {

        Flux<Integer> integers = Flux.create(emitter -> launch(emitter, 6));
        StepVerifier.create(integers.doFinally(signalType -> service.shutdown()).log())
                .expectNextCount(6).verifyComplete();

    }

    private void launch(FluxSink<Integer> integerFluxSink, int count) {
        service.submit(() -> {
            var integer = new AtomicInteger();
            Assertions.assertNotNull(integerFluxSink);
            while (integer.get() < count) {
                double random = Math.random();
                integerFluxSink.next(integer.incrementAndGet());
                sleep((long) (random * 1_000));
            }
            integerFluxSink.complete();
        });
    }

    private void sleep(long s) {
        try {
            Thread.sleep(s);
        } catch (Exception ignored) {
        }
    }

}
