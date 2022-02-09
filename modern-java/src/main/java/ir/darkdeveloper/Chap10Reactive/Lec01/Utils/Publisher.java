package ir.darkdeveloper.Chap10Reactive.Lec01.Utils;

public interface Publisher<T> {
    void subscribe(Subscriber<? super T> subscriber);
}
