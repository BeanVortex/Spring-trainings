package ir.darkdeveloper.sec02;

import java.util.concurrent.Semaphore;

public class Lec02Semaphore {

    public static void main(String[] args) {
        // only 5 thread
        var sem = new Semaphore(5);
        for (int i = 0; i < 10; i++)
            new Thread(new MyRun(sem)).start();

    }
}

class MyRun implements Runnable {

    private final Semaphore sem;

    public MyRun(Semaphore sem) {
        this.sem = sem;
    }

    @Override
    public void run() {
        try {
            sem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long id = Thread.currentThread().getId();
        System.out.println(id + ": started");
        sleep(100);
        System.out.println(id + ": finished");
        sem.release();
    }

    private void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

