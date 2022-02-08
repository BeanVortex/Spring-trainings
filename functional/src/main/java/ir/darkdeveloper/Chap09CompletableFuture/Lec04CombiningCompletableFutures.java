package ir.darkdeveloper.Chap09CompletableFuture;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Lec04CombiningCompletableFutures {

    private static final List<Shop> shops = List.of(new Shop("shop1"),
            new Shop("shop2"),
            new Shop("shop3"),
            new Shop("shop4"),
            new Shop("shop5"),
            new Shop("shop6"),
            new Shop("shop7"),
            new Shop("shop8"),
            new Shop("shop9"),
            new Shop("shop10"));

    private static final ExecutorService executer = Executors.newFixedThreadPool(Math.min(shops.size(), 100),
            (Runnable r) -> {
                var thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            });

    // combine does wait for previous task to complete
    // like compose and should use when previous task's result is not important

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        System.out.println(findAndShip());
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    private static List<String> findAndShip() {
        var futureLists = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(), executer))
                .map(future -> future.thenCombine(
                        CompletableFuture.supplyAsync(() -> Shipping.acceptProduct(), executer)
                                .completeOnTimeout(false, 1, TimeUnit.SECONDS),
                        (res1, res2) -> {
                            return res1 + " shipped: " + res2;
                        }))
                .toList();

        return futureLists.stream()
                .map(CompletableFuture::join)
                .toList();
    }

    public static void delay() {
        try {
            var rand = new Random();
            Thread.sleep(500 + rand.nextInt(3000));
        } catch (InterruptedException e) {
        }
    }

    record Shop(String name) {
        public String getPrice() {
            var price = calculatePrice();
            return String.format("%s:%.2f", name, price);
        }

        private double calculatePrice() {
            delay();
            return Math.random() * 100;
        }
    }

    record Shipping() {

        public static boolean acceptProduct() {
            delay();
            return true;
        }

    }

}
