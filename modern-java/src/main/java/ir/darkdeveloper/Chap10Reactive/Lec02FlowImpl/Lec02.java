package ir.darkdeveloper.Chap10Reactive.Lec02FlowImpl;

import java.util.concurrent.Flow.Publisher;

public class Lec02 {

    public static void main(String[] args) {
        getTemperatures("Kerman").subscribe(new TempSubscriber());
        getTemperaturesInFahrenheit("Kerman").subscribe(new TempSubscriber());
    }

    private static Publisher<TempInfo> getTemperatures(String town) {
        return subscriber -> subscriber.onSubscribe(new TempSubscription(subscriber, town));
    }

    private static Publisher<TempInfo> getTemperaturesInFahrenheit(String town) {
        return subscriber -> {
            var processor = new TempProcessor();
            processor.subscribe(subscriber);
            subscriber.onSubscribe(new TempSubscription(processor, town));
        };
    }

}
