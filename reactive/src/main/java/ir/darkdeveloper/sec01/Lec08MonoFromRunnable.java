package ir.darkdeveloper.sec01;

import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.Mono;

public class Lec08MonoFromRunnable {

    public static void main(String[] args) {

        // Runnable runnable = () -> System.out.println("Afds");

        Mono.fromRunnable(timeConsumingProcess())
                .subscribe(Util.onNext(),
                        Util.onError(),
                        Util.onComplete(null));

    }

    private static Runnable timeConsumingProcess() {
        return () -> {
            Util.sleep(3);
            System.out.println("Operation completed");
        };
    }

}
