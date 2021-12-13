package ir.darkdeveloper.sec03.helper;

import java.util.ArrayList;
import java.util.List;

import ir.darkdeveloper.utils.Util;
import reactor.core.publisher.Flux;

public class NameGenerator {

    public static List<String> getNamesList(int count) {
        var list = new ArrayList<String>();
        for (int i = 0; i < count; i++)
            list.add(getName());
        return list;
    }

    public static Flux<String> getNamesFlux(int count) {
        return Flux.range(0, count)
                .map(i -> getName());
    }

    private static String getName() {
        Util.sleep(1);
        return Util.faker().name().fullName();
    }

}
