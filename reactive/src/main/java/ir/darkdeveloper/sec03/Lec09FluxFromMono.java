package ir.darkdeveloper.sec03;

import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec09FluxFromMono {
    public static void main(String[] args) {
        //from mono to flux
        // var mono = Mono.just("a");
        // var flux = Flux.from(mono);
        // flux.subscribe(Util.onNext());

        //from flux to mono 
        Flux.range(5, 10)
                .filter(i -> i > 7)
                .next()
                .subscribe(Util.onNext());
    }
}