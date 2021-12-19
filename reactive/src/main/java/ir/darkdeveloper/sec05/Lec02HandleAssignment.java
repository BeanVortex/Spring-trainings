package ir.darkdeveloper.sec05;

import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.Flux;

public class Lec02HandleAssignment {

    public static void main(String[] args) {

        Flux.generate(syncSink -> syncSink.next(Util.faker().country().capital()))
                // .map(Object::toString)
                .handle((str, sink) -> {
                    sink.next(str);
                    if (str.equals("Tehran"))
                        sink.complete();
                })
                .subscribe(Util.subscriber());

    }

}
