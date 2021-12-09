package ir.darkdeveloper.sec02;

import java.util.ArrayList;
import java.util.List;

public class Lec01Threads {

    public static void main(String[] args) throws InterruptedException {

        var tTest = new ThreadTest();
        var t1 = new Thread(() -> tTest.increment());
        var t2 = new Thread(() -> tTest.decrement());
        t1.start();
        t2.start();

    }

}

class ThreadTest {

    List<Integer> x = new ArrayList<>();
    int z = 0;

    public synchronized void increment() {
        try {
            System.out.println("increment " + Thread.currentThread());
            x.add(23);
            z++;
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void decrement() {
        System.out.println("decrement " + Thread.currentThread());
        x.remove(0);
        z--;
    }

}
