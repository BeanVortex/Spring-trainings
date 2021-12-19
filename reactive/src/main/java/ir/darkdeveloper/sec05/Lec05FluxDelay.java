package ir.darkdeveloper.sec05;

import java.time.Duration;

import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.Flux;

public class Lec05FluxDelay {

    public static void main(String[] args) {

        Flux.range(1, 100)
                .log()
                .delayElements(Duration.ofSeconds(1))
                .subscribe(Util.subscriber());

        Util.sleep(60);
    }

}
