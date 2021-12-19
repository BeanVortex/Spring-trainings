package ir.darkdeveloper.sec05;

import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.Flux;

public class Lec01Handle {

    public static void main(String[] args) {
        // handle = filter + map

        Flux.range(1, 20)
                .handle((item, syncSink) -> {
                    // filtering
                    if (item % 2 == 0)
                        syncSink.next(item);
                    // mapping
                    else
                        syncSink.next(item + "a");
                })
                .subscribe(Util.subscriber());
    }

}