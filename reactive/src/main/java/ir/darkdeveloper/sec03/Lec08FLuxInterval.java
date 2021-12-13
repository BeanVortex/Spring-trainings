package ir.darkdeveloper.sec03;

import java.time.Duration;

import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.Flux;

public class Lec08FLuxInterval {

    public static void main(String[] args) {
        // publishes item periodically 
        // running non-blocking asynchronously
        // use cases are like sending emails periodically
        Flux.interval(Duration.ofSeconds(1))
                .subscribe(Util.onNext(), Util.onError(), Util.onComplete(null));

        Util.sleep(5);
    }
}
