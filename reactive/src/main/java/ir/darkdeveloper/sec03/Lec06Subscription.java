package ir.darkdeveloper.sec03;

import java.util.concurrent.atomic.AtomicReference;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.Flux;

public class Lec06Subscription {

    public static void main(String[] args) {

        var atomicReference = new AtomicReference<Subscription>();

        Flux.range(1, 20)
                .log()
                .subscribeWith(new Subscriber<Integer>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        System.out.println("Received: " + s);
                        atomicReference.set(s);
                    }

                    @Override
                    public void onNext(Integer t) {
                        System.out.println("onNext: " + t);
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println("onError: " + t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }

                });

        Util.sleep(1);
        atomicReference.get().request(5);
        System.out.println();
        Util.sleep(1);
        atomicReference.get().request(3);
        System.out.println();
        Util.sleep(1);
        atomicReference.get().cancel(); // after cancel , requests are ignored
        System.out.println();
        Util.sleep(1);
        atomicReference.get().request(11);
        System.out.println();

    }
}
