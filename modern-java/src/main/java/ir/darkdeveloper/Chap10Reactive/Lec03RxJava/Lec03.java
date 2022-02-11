package ir.darkdeveloper.Chap10Reactive.Lec03RxJava;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import ir.darkdeveloper.Chap10Reactive.Lec02FlowImpl.TempInfo;

public class Lec03 {

    public static void main(String[] args) throws IOException {
        var strings = Observable.just("First", "Second");
        strings.subscribe(System.out::println, System.out::println, () -> System.out.println("onComplete"));
        Observable<Long> onePerSec = Observable.interval(1, TimeUnit.SECONDS);
        onePerSec.subscribe(
                i -> System.out.println(TempInfo.fetch("Chicago")),
                err -> System.out.println(err.getMessage()),
                System.out::println);

        System.in.read();
    }

}
