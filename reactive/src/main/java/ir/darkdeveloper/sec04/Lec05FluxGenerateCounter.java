package ir.darkdeveloper.sec04;

import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.Flux;

public class Lec05FluxGenerateCounter {

    public static void main(String[] args) {
        // first arg is initial state
        // second arg is a BiFunction accepts state and sink
        Flux.generate(() -> new Count(),
                (state, sink) -> {
                    sink.next(state.counter);
                    if (state.counter == 10)
                        sink.complete();
                    state.counter += 1;
                    return state;
                })
                .subscribe(Util.subscriber());
    }


}

class Count {
    int counter;
}