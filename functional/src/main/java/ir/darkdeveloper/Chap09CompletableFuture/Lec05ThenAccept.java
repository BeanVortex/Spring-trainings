package ir.darkdeveloper.Chap09CompletableFuture;

import java.util.concurrent.CompletableFuture;

public class Lec05ThenAccept {

    public static void main(String[] args) throws InterruptedException {

        var future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return "Some calculated data";
        });

        // as soon as the CompletableFuture completed, run this
        future.thenAccept(data -> System.out.println("Accepted: " + data));

        System.out.println("ran before then accept");

        Thread.sleep(1200);
    }

}
