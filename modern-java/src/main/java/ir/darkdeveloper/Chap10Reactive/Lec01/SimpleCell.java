package ir.darkdeveloper.Chap10Reactive.Lec01;

import java.util.ArrayList;
import java.util.List;

import ir.darkdeveloper.Chap10Reactive.Lec01.Utils.Publisher;
import ir.darkdeveloper.Chap10Reactive.Lec01.Utils.Subscriber;

public class SimpleCell implements Publisher<Integer>, Subscriber<Integer> {

    private int value = 0;
    private String name;
    private List<Subscriber<? super Integer>> subscribers = new ArrayList<>();

    public SimpleCell(String name) {
        this.name = name;
    }

    @Override
    public void onNext(Integer newValue) {
        this.value = newValue;
        System.out.println(name + " : " + value);
        notifyAllSubscribers();
    }

    @Override
    public void subscribe(Subscriber<? super Integer> subscriber) {
        subscribers.add(subscriber);
    }

    private void notifyAllSubscribers() {
        subscribers.forEach(sub -> sub.onNext(this.value));
    }
}
