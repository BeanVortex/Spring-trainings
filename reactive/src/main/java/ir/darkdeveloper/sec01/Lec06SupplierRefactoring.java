package ir.darkdeveloper.sec01;

import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class Lec06SupplierRefactoring {

    public static void main(String[] args) {

        /*   
        getName();
        getName()
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(Util.onNext());
        getName();
        Util.sleep(4);
        */

        getName();
        String data = getName()
                .subscribeOn(Schedulers.boundedElastic())
                .block();
        System.out.println(data);
        getName();

    }

    private static Mono<String> getName() {
        System.out.println("entered getName method ...");
        return Mono.fromSupplier(() -> {
            System.out.println("Generating name ...");
            Util.sleep(3);
            return Util.faker().name().fullName();
        }).map(String::toUpperCase);
    }

}