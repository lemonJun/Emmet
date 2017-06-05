package io;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * 把一个大任务分割成若干个小任务
 *
 * @author WangYazhou
 * @date  2017年6月5日 下午6:25:19
 * @see
 */
public class TestForkJoin {

    public static void main(String[] args) {
        try {
            int end = 100;

            ForkJoinPool forkJoinPool = new ForkJoinPool();
            ForkJoinTest task = new ForkJoinTest(1, end);
            Future<Long> future = forkJoinPool.submit(task);
            long t1 = System.currentTimeMillis();
            System.out.println(future.get());
            long t2 = System.currentTimeMillis();
            task.cal(1, end);
            long t3 = System.currentTimeMillis();

            System.out.println("1:" + (t2 - t1));
            System.out.println("2:" + (t3 - t2));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
