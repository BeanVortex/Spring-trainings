package ir.darkdeveloper.sec02;

import java.util.concurrent.CompletableFuture;

public class Lec06CompletableFuture {

    public static void main(String[] args) throws InterruptedException {
        var future = new CompletableFuture<Integer>();
        var future2 = new CompletableFuture<Integer>();
        process(future);
        process(future2);
        future.complete(5);
        future2.complete(0);
        Thread.sleep(100);
    }

    private static void process(CompletableFuture<Integer> future) {
        future
                .thenApply(data -> data / 0)
                .exceptionally(Lec06CompletableFuture::handle)
                .thenApply(data -> data * 2)
                .thenApply(data -> data + 5)
                .thenAcceptAsync(System.out::println);
    }

    private static int handle(Throwable throwable) {
        System.out.println("ERROR: " + throwable.getMessage());
        return 4;
    }
}
