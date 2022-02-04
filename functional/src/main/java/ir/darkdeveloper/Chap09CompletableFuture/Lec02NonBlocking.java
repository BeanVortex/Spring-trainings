package ir.darkdeveloper.Chap09CompletableFuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


public class Lec02NonBlocking {

    public static void main(String[] args) {
        var shops = List.of(new Shop("shop1"),
                new Shop("shop2"),
                new Shop("shop3"),
                new Shop("shop4"),
                new Shop("shop5"));
        long start = System.currentTimeMillis();
        System.out.println(findPricesAsync(shops));
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    public static List<String> findPricesAsync(List<Shop> shops) {
        var cf = shops.stream()
                .map(shop -> CompletableFuture
                        .supplyAsync(() -> String.format("%s price is %.2f", shop.name(), shop.getPrice())))
                .collect(Collectors.toList());
        // separated streams because join acts sequential
        return cf.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

    }

    public static List<String> findPricesParallel(List<Shop> shops) {
        return shops.stream()
                .parallel()
                .map(shop -> String.format("%s price is %.2f", shop.name(), shop.getPrice()))
                .collect(Collectors.toList());

    }

    public static List<String> findPrices(List<Shop> shops) {
        return shops.stream()
                .map(shop -> String.format("%s price is %.2f", shop.name(), shop.getPrice()))
                .collect(Collectors.toList());

    }

    record Shop(String name) {
        public double getPrice() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return Math.random() * 100;
        }
    }

}
