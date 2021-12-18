package ir.darkdeveloper.sec04;

import ir.darkdeveloper.sec04.helper.NameProducerCreate;
import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.Flux;

public class Lec01FluxCreate {

    public static void main(String[] args) {

        Flux.create(fluxSink -> {

            var country = Util.faker().country().name();

            do {
                country = Util.faker().country().name();
                fluxSink.next(country);
            } while (!country.startsWith("Iran"));
            fluxSink.complete();
        }).subscribe(Util.subscriber());

        var nameProducerCreate = new NameProducerCreate();

        Flux.create(nameProducerCreate)
                .subscribe(Util.subscriber());

        nameProducerCreate.produce();

        Runnable run = nameProducerCreate::produce;

        // Flux Sink is thread safe
        for (int i = 0; i < 10; i++) {
            new Thread(run).start();
        }

        Util.sleep(2);

    }

}
