package ir.darkdeveloper.sec01;

import java.util.concurrent.CompletableFuture;

import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.Mono;

public class Lec07MonoFromFuture {

    public static void main(String[] args) {
        Mono<String> mono = Mono.fromFuture(getName());
        System.out.println("before subscribe");
        mono.subscribe(Util.onNext());
        System.out.println("after subscribe");
        Util.sleep(1);
    }

    private static CompletableFuture<String> getName() {
        return CompletableFuture.supplyAsync(() -> Util.faker().name().fullName());
    }

}
