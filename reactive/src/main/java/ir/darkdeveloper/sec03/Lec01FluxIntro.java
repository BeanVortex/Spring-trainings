package ir.darkdeveloper.sec03;

import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.Flux;

public class Lec01FluxIntro {

    public static void main(String[] args) {
        Flux<Integer> flux = Flux.just(1, 2, 4, 5, 6, 3);
        Flux<Integer> flux2= Flux.empty();
        Flux<Integer> flux3= Flux.error(new Exception("err"));
        Flux<Object> flux4 = Flux.just(1, 'f', "string", 2.5f, 2.3d, 3L);
        flux.subscribe(Util.onNext(), Util.onError(), Util.onComplete(null));
        System.out.println();
        flux2.subscribe(Util.onNext(), Util.onError(), Util.onComplete(null));
        System.out.println();
        flux3.subscribe(Util.onNext(), Util.onError(), Util.onComplete(null));
        System.out.println();
        flux4.subscribe(Util.onNext(), Util.onError(), Util.onComplete(null));
    }
}
