package ir.darkdeveloper.sec02;

import java.util.ArrayList;
import java.util.List;

public class Lec01Threads {

    public static void main(String[] args) throws InterruptedException {

        var tTest = new ThreadTest();
        var t1 = new Thread(() -> tTest.increment());
        var t2 = new Thread(() -> tTest.decrement());
        t2.start();
        t1.start();

    }

}

class ThreadTest {

    List<Integer> x = new ArrayList<>();

    public void increment() {
        synchronized (x) {
            x.add(23);
            x.notify();
            System.out.println("increment " + Thread.currentThread());
        }
    }

    public void decrement() {
        synchronized (x) {
            while (x.size() == 0)
                try {
                    x.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            var fetched = x.remove(0);
            System.out.println("decrement " + fetched + " " + Thread.currentThread());
        }
    }

}
