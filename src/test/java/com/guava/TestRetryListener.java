package com.guava;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Predicates;
import com.takin.emmet.concurrent.retry.Retryer;
import com.takin.emmet.concurrent.retry.RetryerBuilder;
import com.takin.emmet.concurrent.retry.StopStrategies;
import com.takin.emmet.concurrent.retry.WaitStrategies;

public class TestRetryListener {
    private static Callable<Boolean> maySuccessTask = new Callable<Boolean>() {
        private int times = 0;

        @Override
        public Boolean call() throws Exception {
            System.out.println("called");
            times++;

            if (times == 1) {
                throw new NullPointerException();
            } else if (times == 2) {
                return false;
            } else {
                return true;
            }
        }
    };

    public static void main(String[] args) throws Exception {
        // 异常或者返回false都继续重试  
        // 每秒重试一次  
        // 最多重试5次  
        // 允许添加多个RetryListener  
        Retryer<Boolean> retryer = RetryerBuilder.<Boolean> newBuilder().retryIfResult(Predicates.equalTo(false))//
                        .retryIfException().withWaitStrategy(WaitStrategies.fixedWait(1, TimeUnit.SECONDS))//
                        .withStopStrategy(StopStrategies.stopAfterAttempt(5))//
                        .withRetryListener(new MyRetryListener<>())//
                        .build();

        try {
            retryer.call(maySuccessTask);
        } catch (Exception e) {
            System.err.println("still failed after retry.");
            e.printStackTrace();
        }
    }
}
