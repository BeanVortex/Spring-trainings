package ir.darkdeveloper.sec01;

import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.Mono;

public class Lec04MonoEmptyOrError {

    public static void main(String[] args) {

        userRepo(1)
                .subscribe(
                        Util.onNext(),
                        Util.onError(),
                        Util.onComplete(null));

    }

    private static Mono<String> userRepo(Integer id) {
        if (id == 1)
            return Mono.just(Util.faker().name().firstName());
        else if (id == 2)
            return Mono.empty();
        else
            return Mono.error(new Exception("Not found with id of " + id));
    }
}
