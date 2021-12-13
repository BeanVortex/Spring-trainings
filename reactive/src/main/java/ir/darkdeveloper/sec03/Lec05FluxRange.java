package ir.darkdeveloper.sec03;

import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.Flux;

public class Lec05FluxRange {

    public static void main(String[] args) {
        Flux.range(1, 5)
                .log()
                .map(i -> Util.faker().name().firstName())
                .log()
                .map(str -> str.length())
                .log()
                .subscribe(Util.onNext(), Util.onError(), Util.onComplete(null));
    }

}
