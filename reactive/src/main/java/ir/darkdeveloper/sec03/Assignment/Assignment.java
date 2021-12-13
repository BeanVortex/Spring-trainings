package ir.darkdeveloper.sec03.Assignment;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import ir.darkdeveloper.utils.Util;

public class Assignment {

    public static void main(String[] args) throws InterruptedException {

        var latch = new CountDownLatch(1);

        StockPricePublisher.getPrice()
                .subscribeWith(new Subscriber<Integer>() {

                    private Subscription sub;

                    @Override
                    public void onSubscribe(Subscription s) {
                        // it means, provide all the data you can get
                        s.request(Long.MAX_VALUE );
                        sub = s;
                    }

                    @Override
                    public void onNext(Integer price) {
                        System.out.println(LocalDateTime.now() + " : Price : " + price);
                        if (price > 110 || price < 90) {
                            sub.cancel();
                            latch.countDown();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        latch.countDown();
                        System.out.println("onError");
                    }

                    @Override
                    public void onComplete() {
                        latch.countDown();
                        System.out.println("onComplete");
                    }

                });
        latch.await();
    }
}
