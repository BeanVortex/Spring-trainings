package ir.darkdeveloper.Chap03Parallelism;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class Lec02ForkJoinPool {

    public static void main(String[] args) {
        var start1 = System.currentTimeMillis();
        System.out.println(forkJoinSum(100_000_000));
        var end1 = System.currentTimeMillis();
        System.out.println(end1 - start1);

        var start2 = System.currentTimeMillis();
        System.out.println(streamSum(100_000_000));
        var end2 = System.currentTimeMillis();
        System.out.println(end2 - start2);

        var start3 = System.currentTimeMillis();
        System.out.println(parallelStreamSum(100_000_000));
        var end3 = System.currentTimeMillis();
        System.out.println(end3 - start3);

    }

    private static long forkJoinSum(long n) {

        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCal(numbers);
        //this task is executed by a thread of the pool that in turn calls the compute method of the task.
        return new ForkJoinPool().invoke(task);
    }

    private static long streamSum(long n) {
        return LongStream.rangeClosed(1, n).sum();
    }

    private static long parallelStreamSum(long n) {
        return LongStream.rangeClosed(1, n).parallel().sum();
    }

}

class ForkJoinSumCal extends RecursiveTask<Long> {

    private final long[] numbers;
    private final int start;
    private final int end;
    private static final long THRESHOLD = 10_000;

    public ForkJoinSumCal(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    private ForkJoinSumCal(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {

        var length = end - start;
        if (length <= THRESHOLD)
            return computeSequentially();

        var leftTask = new ForkJoinSumCal(numbers, start, start + length / 2);
        // separate task
        leftTask.fork();
        var rightTask = new ForkJoinSumCal(numbers, start + length / 2, end);

        var rightResult = rightTask.compute();
        // waits until leftTask is done
        var leftResult = leftTask.join();

        return rightResult + leftResult;
    }

    private long computeSequentially() {
        long sum = 0;
        for (int i = start; i < end; i++)
            sum += numbers[i];
        return sum;
    }

}