package ir.darkdeveloper.sec01;

import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.Mono;

public class Lec06SupplierRefactoring {

    public static void main(String[] args) {
        getName();
        getName();
        getName().subscribe();
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