package com.example._05reactor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.FluxSink;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class HotStreamTest1 {

    // Cold Publisher: For every subscriber, the items start from the beginning.
    // Hot Publisher: Doesn't publish from the beginning. Instead, the values keep on going independently. Any subscriber joining will get the same value that all others do.

    @Test
    void hot() {
        var first = new ArrayList<Integer>();
        var second = new ArrayList<Integer>();

        var emitter = EmitterProcessor.<Integer>create(2);
        var sink = emitter.sink();

        emitter.subscribe(collect(first));
        sink.next(1);
        sink.next(2);

        emitter.subscribe(collect(second));
        sink.next(3);
        sink.complete();
        Assertions.assertTrue(first.size() > second.size());
    }

    Consumer<Integer> collect(List<Integer> collection) {
        return collection::add;
    }
}
