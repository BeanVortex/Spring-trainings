package ir.darkdeveloper.sec04;

import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.Flux;

public class Lec03FluxIfCancelled {

    public static void main(String[] args) {
        // only one stance of flux sink
        Flux.create(fluxSink -> {

            var country = Util.faker().country().name();

            do {
                country = Util.faker().country().name();
                System.out.println("Emitting : " + country);
                fluxSink.next(country);
            } while (!country.startsWith("Iran") && !fluxSink.isCancelled());
            
            fluxSink.complete();
        })
        .take(5)
        .subscribe(Util.subscriber());

    }

}
