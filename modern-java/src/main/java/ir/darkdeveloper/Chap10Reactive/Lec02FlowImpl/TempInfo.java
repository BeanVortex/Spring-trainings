package ir.darkdeveloper.Chap10Reactive.Lec02FlowImpl;

import java.util.Random;

public record TempInfo(String town, int temp) {

    public static final Random rand = new Random();

    public static TempInfo fetch(String town) throws InterruptedException {
        if (rand.nextInt(10) == 0)
            throw new RuntimeException("Error!");
        Thread.sleep(100);
        System.out.println(Thread.currentThread());
        return new TempInfo(town, rand.nextInt(40));
    }
}