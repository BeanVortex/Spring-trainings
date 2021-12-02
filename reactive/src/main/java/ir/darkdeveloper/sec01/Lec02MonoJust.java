package ir.darkdeveloper.sec01;

import reactor.core.publisher.Mono;

public class Lec02MonoJust {

    public static void main(String[] args) {

        Mono<Integer> mono = Mono.just(1);

        mono.subscribe(i -> {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
            System.out.println("Mono received: " + i);
        });
    }
}