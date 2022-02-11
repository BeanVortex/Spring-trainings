package ir.darkdeveloper.Chap10Reactive.Lec02FlowImpl;

import java.util.concurrent.Flow.Processor;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class TempProcessor implements Processor<TempInfo, TempInfo> {

    private Subscriber<? super TempInfo> subscriber;

    @Override
    public void onComplete() {
        subscriber.onComplete();
    }

    @Override
    public void onError(Throwable t) {
        subscriber.onError(t);
    }

    @Override
    public void onNext(TempInfo t) {
        // convert from celsius to fahrenheit 
        subscriber.onNext(new TempInfo(t.town(), (9 / 5 * (t.temp() + 32))));
    }

    @Override
    public void onSubscribe(Subscription s) {
        subscriber.onSubscribe(s);
    }

    @Override
    public void subscribe(Subscriber<? super TempInfo> s) {
        subscriber = s;
    }

}
