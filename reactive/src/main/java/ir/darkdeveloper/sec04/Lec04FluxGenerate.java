package ir.darkdeveloper.sec04;

import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.Flux;

public class Lec04FluxGenerate {

    public static void main(String[] args) {

        // emitting one item
        // in a loop
        Flux.generate(syncSink -> {
            syncSink.next(Util.faker().cat().breed()); // 1
            // syncSink.next(Util.faker().cat().breed()); // Error
        })
                .take(4)
                .subscribe(Util.subscriber());

        Flux.generate(syncSink -> {
            var country = Util.faker().country().name();
            syncSink.next(country);
            if (country.equals("Libya"))
                syncSink.complete();
        })
                .subscribe(Util.subscriber());
    }

}
