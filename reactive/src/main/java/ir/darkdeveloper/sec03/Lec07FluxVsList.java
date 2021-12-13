package ir.darkdeveloper.sec03;

import ir.darkdeveloper.sec03.helper.NameGenerator;
import ir.darkdeveloper.utils.Util;

public class Lec07FluxVsList {

    public static void main(String[] args) {
        var list = NameGenerator.getNamesList(5); // waiting for all items
        System.out.println(list);
        var flux = NameGenerator.getNamesFlux(5);
        flux.subscribe(Util.onNext(), Util.onError(), Util.onComplete(null)); // getting single data when ready
    }
}
