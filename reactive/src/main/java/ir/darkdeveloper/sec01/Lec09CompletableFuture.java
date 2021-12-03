package ir.darkdeveloper.sec01;

import java.util.concurrent.CompletableFuture;

public class Lec09CompletableFuture {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("before completable future");
        Thread a = Thread.currentThread();
        CompletableFuture.runAsync(() -> {
            try {
                Thread b = Thread.currentThread();
                System.out.println(a.getName());
                System.out.println(b.getName());
                Thread.sleep(1000);
                System.out.println("CompletableFuture");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("after completable future");
        Thread.sleep(2000);
    }
}
