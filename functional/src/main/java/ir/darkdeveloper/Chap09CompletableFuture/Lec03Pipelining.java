package ir.darkdeveloper.Chap09CompletableFuture;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Lec03Pipelining {

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

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println(findPricesAsyncCustomExecuter());
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    public static List<String> findPricesAsyncCustomExecuter() {
        var pricesFuture = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(), executer))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future
                        .thenCompose(
                                quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executer)))
                .toList();
        return pricesFuture.stream()
                .map(CompletableFuture::join)
                .toList();
    }

    public static List<String> findPricesAsync() {
        var pricesFuture = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice()))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future
                        .thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote))))
                .toList();
        return pricesFuture.stream()
                .map(CompletableFuture::join)
                .toList();
    }

    public static List<String> findPrices() {
        return shops.stream()
                .map(Shop::getPrice)
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(Collectors.toList());

    }

    public static void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }

    record Shop(String name) {
        public String getPrice() {
            var rand = new Random();
            var price = calculatePrice();
            var code = Discount.Code.values()[rand.nextInt(Discount.Code.values().length)];
            return String.format("%s:%.2f:%s", name, price, code);
        }

        private double calculatePrice() {
            delay();
            return Math.random() * 100;
        }
    }

    record Discount() {
        public enum Code {
            NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);

            private final int percentage;

            Code(int percentage) {
                this.percentage = percentage;
            }
        }

        private static String applyDiscount(Quote quote) {
            return quote.shopName() + " price is " + Discount.apply(quote.price(), quote.discountCode());
        }

        private static double apply(double price, Code code) {
            delay();
            return price * (100 - code.percentage) / 100;
        }
    }

    record Quote(String shopName, double price, Discount.Code discountCode) {
        public static Quote parse(String str) {
            var splitted = str.split(":");
            var shop = splitted[0];
            var price = Double.parseDouble(splitted[1]);
            var code = Discount.Code.valueOf(splitted[2]);
            return new Quote(shop, price, code);
        }
    }

}
