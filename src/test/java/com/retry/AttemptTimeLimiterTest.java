/*
 * Copyright 2012-2013 Ray Holder
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.retry;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.util.concurrent.UncheckedTimeoutException;
import com.takin.emmet.concurrent.retry.AttemptTimeLimiters;
import com.takin.emmet.concurrent.retry.RetryException;
import com.takin.emmet.concurrent.retry.Retryer;
import com.takin.emmet.concurrent.retry.RetryerBuilder;

import junit.framework.Assert;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class AttemptTimeLimiterTest {

    Retryer<Void> r = RetryerBuilder.<Void> newBuilder().withAttemptTimeLimiter(AttemptTimeLimiters.<Void> fixedTimeLimit(1, TimeUnit.SECONDS)).build();

    @Test
    public void testAttemptTimeLimit() throws ExecutionException, RetryException {
        try {
            r.call(new SleepyOut(0L));
        } catch (ExecutionException e) {
            Assert.fail("Should not timeout");
        }

        try {
            r.call(new SleepyOut(10 * 1000L));
            Assert.fail("Expected timeout exception");
        } catch (ExecutionException e) {
            // expected
            Assert.assertEquals(UncheckedTimeoutException.class, e.getCause().getClass());
        }
    }

    static class SleepyOut implements Callable<Void> {

        final long sleepMs;

        SleepyOut(long sleepMs) {
            this.sleepMs = sleepMs;
        }

        @Override
        public Void call() throws Exception {
            Thread.sleep(sleepMs);
            System.out.println("I'm awake now");
            return null;
        }
    }
}
