package ir.darkdeveloper.Chap10Reactive.Lec01SimpleImpl.Utils;

public interface Publisher<T> {
    void subscribe(Subscriber<? super T> subscriber);
}
