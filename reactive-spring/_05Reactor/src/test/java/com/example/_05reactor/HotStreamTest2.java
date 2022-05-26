package com.example._05reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class HotStreamTest2 {


    @Test
    void hot() throws InterruptedException {
        var factor = 10;
        log.info("start");
        var cdl = new CountDownLatch(2);
        var share = Flux.range(0, 10).delayElements(Duration.ofMillis(factor)).share();
        var first = new ArrayList<Integer>();
        var second = new ArrayList<Integer>();

        share.doFinally(signalTypeConsumer(cdl)).subscribe(collect(first));
        Thread.sleep(factor * 2);
        share.doFinally(signalTypeConsumer(cdl)).subscribe(collect(second));

        cdl.await(5, TimeUnit.SECONDS);
        assertTrue(first.size() > second.size());
        log.info("stop");
    }

    private Consumer<SignalType> signalTypeConsumer(CountDownLatch cdl) {
        return signal -> {
            if (signal.equals(SignalType.ON_COMPLETE)) {
                try {
                    cdl.countDown();
                    log.info("await()...");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    Consumer<Integer> collect(List<Integer> collection) {
        return collection::add;
    }
}
