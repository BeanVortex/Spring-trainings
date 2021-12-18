package ir.darkdeveloper.sec04;

import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.Flux;

public class Lec02FluxTake {

    public static void main(String[] args) {

        Flux.range(1, 10)
                .log()
                .take(6)
                .log()
                .subscribe(Util.subscriber());
 
    }

}
