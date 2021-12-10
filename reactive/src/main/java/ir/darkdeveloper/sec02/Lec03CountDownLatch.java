package ir.darkdeveloper.sec02;

import java.util.concurrent.CountDownLatch;

public class Lec03CountDownLatch {

    public static void main(String[] args) {
        // It means 2 countDown methods should be called to pass the await method 
        var latch = new CountDownLatch(2);
        new Thread(new SendEmail(latch)).start();
        new Thread(new VirusCheck(latch)).start();
        new Thread(new IndexEmail(latch)).start();
    }
}

class SendEmail implements Runnable {
    private final CountDownLatch latch;

    public SendEmail(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // err instantly prints and won't save it in buffer
        System.err.println("Send Email started");
        Job.job();
        System.err.println("Send Email finished");
    }

}

class VirusCheck implements Runnable {
    private final CountDownLatch latch;

    public VirusCheck(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        System.err.println("Scan started");
        Job.job();
        System.err.println("Scan finished");
        latch.countDown();
        Job.job();
        System.err.println("Scan finalized");
    }
}

class IndexEmail implements Runnable {
    private final CountDownLatch latch;

    public IndexEmail(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        System.err.println("index started");
        Job.job();
        System.err.println("index finished");
        latch.countDown();
        Job.job();
        System.err.println("index finalized");
    }
}

class Job {
    public static void job() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}