package ir.darkdeveloper.sec05;

import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.Flux;

public class Lec03DoCallbacks {

    public static void main(String[] args) {
        Flux.create(sink -> {
            System.out.println("Inside create");
            for (int i = 0; i < 5; i++)
                sink.next(i);
            sink.complete();
            System.out.println("__Completed");
        })
                // lifecycle callbacks
                // order for doFirst and doFinally are reverse and orders matter for these
                .doFirst(() -> System.out.println("doFirst 1"))
                .doFirst(() -> System.out.println("doFirst 2"))
                .doFirst(() -> System.out.println("doFirst 3"))
                .doOnNext(item -> System.out.println("doOnNext : " + item))
                .doOnSubscribe(subscription -> System.out.println("doOnSubscribe 1: " + subscription))
                .doOnSubscribe(subscription -> System.out.println("doOnSubscribe 2: " + subscription))
                .doOnRequest(length -> System.out.println("doOnRequest  : " + length))
                .doOnComplete(() -> System.out.println("doOnComplete 2"))
                .doOnTerminate(() -> System.out.println("doOnTerminate"))
                .doOnCancel(() -> System.out.println("doOnCancel"))
                .doOnDiscard(Object.class, obj -> System.out.println("doOnDiscard : " + obj))
                .take(3)
                .doFinally(signalType -> System.out.println("doFinally 1: " + signalType))
                .doFinally(signalType -> System.out.println("doFinally 2: " + signalType))
                .subscribe(Util.subscriber());
    }

}