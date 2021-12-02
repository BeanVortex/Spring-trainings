package ir.darkdeveloper.sec01;

import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.Mono;

public class Lec03MonoSubscribe {


    public static void main(String[] args) {
        Mono<Integer> mono = Mono.just("dollar")
                .map(String::length)
                .map(integer -> integer / 0);

        // 1
        // mono.subscribe();

        // 2
        // mono.subscribe(
        //         data -> System.out.println(data),
        //         err -> System.out.println(err.getMessage()),
        //         () -> System.out.println("OnComplete"));

        // 3 
        mono.subscribe(
                Util.onNext(),
                Util.onError(),
                Util.onComplete(null));
    }
}
