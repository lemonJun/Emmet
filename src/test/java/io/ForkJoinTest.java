package io;

import java.util.concurrent.RecursiveTask;

public class ForkJoinTest extends RecursiveTask<Long> {

    public final static int THRESHOLD = 10;
    private int start;
    private int end;

    public ForkJoinTest(int s, int e) {
        start = s;
        end = e;
    }

    @Override
    protected Long compute() {
        long sum = 0L;
        boolean devide = (end - start) <= THRESHOLD;
        if (devide) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            int mid = (start + end) >> 1;
            ForkJoinTest leftTask = new ForkJoinTest(start, mid);
            ForkJoinTest rightTask = new ForkJoinTest(mid + 1, end);
            leftTask.fork();
            rightTask.fork();
            sum += leftTask.join();
            sum += rightTask.join();
        }
        return sum;
    }

    public long cal(int start, int end) {
        long sum = 0L;
        for (int i = start; i <= end; i++) {
            sum += i;
        }
        return sum;
    }
}
