package io;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class ForkjoinTest2 {

    public static void main(String[] args) {
        try {
            ForkJoinPool pool = new ForkJoinPool();
            One one1 = new One(0, 100);
            Future<Integer> future = pool.submit(one1);
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    static class One extends RecursiveTask<Integer> {

        private int start;
        private int end;;

        public One(int start, int end) {
            this.start = start;
            this.end = end;
            System.out.println("start:" + start + " end:" + end);
        }

        @Override
        protected Integer compute() {
            int sum = 0;

            boolean split = (end - start) > 5;
            if (split) {
                int mid = (end + start) >> 1;
                One one2 = new One(start, mid);
                One one3 = new One(mid + 1, end);
                one2.fork();
                one3.fork();
                sum += one2.join();
                sum += one3.join();
            } else {
                for (int i = start; i <= end; i++) {
                    sum += i;
                }
            }
            System.out.println("start:" + start + " end:" + end + " sum:" + sum);
            return sum;
        }

    }

}
