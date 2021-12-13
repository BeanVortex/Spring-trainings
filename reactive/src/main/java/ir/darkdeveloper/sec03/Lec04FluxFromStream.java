package ir.darkdeveloper.sec03;

import java.util.List;

import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.Flux;

public class Lec04FluxFromStream {

    public static void main(String[] args) {
        var list = List.of(1, 2, 3, 4, 5);
        var stream = list.stream();
        // stream.forEach(System.out::println); // CLOSED 
        // stream.forEach(System.out::println);

        var streamFlux = Flux.fromStream(stream);
        streamFlux.subscribe(Util.onNext(), Util.onError(), Util.onComplete(null)); // Closes again when using stream
        streamFlux.subscribe(Util.onNext(), Util.onError(), Util.onComplete(null));

        var streamFlux2 = Flux.fromStream(() -> list.stream()); // better approach
        streamFlux2.subscribe(Util.onNext(), Util.onError(), Util.onComplete(null));
        streamFlux2.subscribe(Util.onNext(), Util.onError(), Util.onComplete(null));

    }

}
