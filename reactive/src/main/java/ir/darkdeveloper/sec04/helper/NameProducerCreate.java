package ir.darkdeveloper.sec04.helper;

import java.util.function.Consumer;

import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.FluxSink;

public class NameProducerCreate implements Consumer<FluxSink<String>> {

    private FluxSink<String> sink;

    @Override
    public void accept(FluxSink<String> t) {
        sink = t;
    }

    public void produce() {
        var name = Util.faker().name().fullName();
        var thread = Thread.currentThread().getName();
        sink.next(thread + " : " + name);
    }

}
