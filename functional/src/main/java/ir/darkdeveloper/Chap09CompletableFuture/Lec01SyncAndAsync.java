package ir.darkdeveloper.Chap09CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Lec01SyncAndAsync {

    /**
     * Java Uses thread provided by ForkJoinPoll to Perform non-blocking Asynchronous action
     * While javascript has only one Thread and does async actions 
     */

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        var shop = new Shop();
        shop.getPriceAsync().thenAccept(v -> System.out.println("Price got async " + v));
        var oldStyleFuture = shop.getPriceAsync();
        System.out.println("Price got old async " + oldStyleFuture.get());
        System.out.println("Price got sync " + shop.getPriceSync());

    }

    public static void delay() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
    }

    public static void delay(int num) {
        try {
            Thread.sleep(num);
        } catch (Exception e) {
        }
    }

    record Shop() {
        public double getPriceSync() {
            Lec01SyncAndAsync.delay();
            return 2.54;
        }

        public CompletableFuture<Double> getPriceAsync() {
            return CompletableFuture.supplyAsync(() -> {
                Lec01SyncAndAsync.delay(100);
                return 2.5d;
            });
        }

        public Future<Double> getPriceAsyncOld() {
            var futurePrice = new CompletableFuture<Double>();
            new Thread(() -> {
                Lec01SyncAndAsync.delay(100);
                futurePrice.complete(2.5d);
            }).start();
            return futurePrice;
        }
    }

}
