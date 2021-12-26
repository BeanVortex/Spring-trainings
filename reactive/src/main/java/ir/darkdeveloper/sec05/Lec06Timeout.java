package ir.darkdeveloper.sec05;

import java.time.Duration;

import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.Flux;

public class Lec06Timeout {

    public static void main(String[] args) {

        getOrderNumbers()
                .timeout(Duration.ofSeconds(5), fallback())
                .subscribe(Util.subscriber());

        Util.sleep(60);

    }

    private static Flux<Integer> getOrderNumbers() {
        return Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1));
    }

    private static Flux<Integer> fallback() {
        return Flux.range(100, 10)
                .delayElements(Duration.ofMillis(200));
    }

}
