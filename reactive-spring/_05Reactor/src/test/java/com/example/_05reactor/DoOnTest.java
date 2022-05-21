package com.example._05reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Signal;
import reactor.core.publisher.SignalType;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
public class DoOnTest {

    @Test
    public void doOn() {
        var signals = new ArrayList<Signal<Integer>>();
        var nextValues = new ArrayList<Integer>();
        var subscriptions = new ArrayList<Subscription>();
        var exceptions = new ArrayList<Throwable>();
        var finallySignals = new ArrayList<SignalType>();
        Flux<Integer> on = Flux
                .<Integer>create(sink -> {
                    sink.next(1);
                    sink.next(2);
                    sink.next(3);
                    sink.error(new IllegalArgumentException("oops!"));
                    sink.complete();
                })
                .doOnNext(nextValues::add)
                .doOnEach(signals::add)
                .doOnSubscribe(subscriptions::add)
                .doOnError(IllegalArgumentException.class, exceptions::add)
                .doFinally(finallySignals::add);

        StepVerifier
                .create(on)
                .expectNext(1, 2, 3)
                .expectError(IllegalArgumentException.class)
                .verify();
        log.info("signals:");
        signals.forEach(this::info);
        Assertions.assertEquals(4, signals.size());
        log.info("finally signals:");
        finallySignals.forEach(this::info);
        Assertions.assertEquals(finallySignals.size(), 1);
        log.info("subscriptions:");
        subscriptions.forEach(this::info);
        Assertions.assertEquals(subscriptions.size(), 1);
        log.info("exceptions signals:");
        exceptions.forEach(this::info);
        Assertions.assertEquals(exceptions.size(), 1);
        Assertions.assertTrue(exceptions.get(0) instanceof IllegalArgumentException);
        log.info("nextval signals:");
        nextValues.forEach(this::info);
        Assertions.assertEquals(Arrays.asList(1, 2, 3), nextValues);
    }

    void info(Object obj) {
        log.info(String.valueOf(obj));
    }
}
