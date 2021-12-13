package ir.darkdeveloper.sec03.Assignment;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.Flux;

public class StockPricePublisher {

    public static Flux<Integer> getPrice() {
        var atomicInt = new AtomicInteger(100);

        return Flux.interval(Duration.ofSeconds(1))
                .map(i -> atomicInt.getAndAccumulate(
                        Util.faker().random().nextInt(-5, 5),
                        Integer::sum
                        ));
    }

}
