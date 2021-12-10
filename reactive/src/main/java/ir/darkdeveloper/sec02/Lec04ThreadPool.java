package ir.darkdeveloper.sec02;

import java.util.concurrent.Executors;

public class Lec04ThreadPool {

    public static void main(String[] args) {
        // fixedThreadPool();
        // singleThreadPool();
        cachedThreadPool();
    }

    private static void fixedThreadPool() {
        // since creating a thread costs a lot, so Thread pool is here to reuse finished threads
        var e = Executors.newFixedThreadPool(2);
        // defining a task
        Runnable run = () -> {
            for (int i = 0; i < 4; i++)
                System.out.println(Thread.currentThread().getId() + ": " + i);
        };

        // executing tasks
        for (int i = 0; i < 3; i++)
            e.execute(run);

        e.shutdown();
    }

    private static void singleThreadPool() {
        // only one thread
        var e = Executors.newSingleThreadExecutor();
        // defining a task
        Runnable run = () -> {
            for (int i = 0; i < 4; i++)
                System.out.println(Thread.currentThread().getId() + ": " + i);
        };

        // executing tasks
        for (int i = 0; i < 3; i++)
            e.execute(run);

        e.shutdown();
    }

    private static void cachedThreadPool() {
        // created threads with no limits and caches for next tasks
        var e = Executors.newCachedThreadPool();
        // defining a task
        Runnable run = () -> {
            for (int i = 0; i < 4; i++)
                System.out.println(Thread.currentThread().getId() + ": " + i);
        };

        // executing tasks
        for (int i = 0; i < 3; i++)
            e.execute(run);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        // used cached thread
        e.execute(run);
        e.shutdown();
    }
}
