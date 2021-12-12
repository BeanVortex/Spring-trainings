package ir.darkdeveloper.sec02;

public class Lec05Volatile {

    public static void main(String[] args) {
        var t = new threads();
        t.runThreads();
    }
}

class threads {

    // solves visibility problem
    // if flag changes in a thread, other thread refreshes the value
    // only assignment works not increments or decrements
    // because when ++ or -- we read and write the value, so in this case should use
    // Atomic variables
    volatile boolean flag = true;
    volatile int val = 0;

    public void runThreads() {

        var t1 = new Thread(() -> {
            try {
                for (int i = 0; i < 2; i++) {
                    Thread.sleep(1000);
                    if (i == 1)
                        flag = false;
                    System.out.println(Thread.currentThread().getId() + ": " + flag + "_" + val);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            val = 5;
            flag = true;

        });
        var t2 = new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) {
                    Thread.sleep(1000);

                    System.out.println(Thread.currentThread().getId() + ": " + flag + "_" + val);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
    }
}