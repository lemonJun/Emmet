/*
 * Copyright (c) 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import com.takin.emmet.io.traffic.shm.queue.Block;
import com.takin.emmet.io.traffic.shm.queue.Queue;

/**
 * @author cuiyi
 */
@BenchmarkMode(Mode.Throughput)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(5)
public class TestBenchmark {

    Queue queue = Queue.map("/Users/peptos/shm", 2000000000L, 1, 0);

    @Setup(Level.Iteration)
    public void setup() {
        queue.init();
    }

    @Benchmark
    @Measurement(time = 1, timeUnit = TimeUnit.SECONDS)
    public void measurePut() throws Throwable {
        String str = "----------------------------------------------------";
        byte[] bytes = str.getBytes("UTF-8");

        queue.put(new Block(bytes));
    }

    @Benchmark
    @Measurement(time = 1, timeUnit = TimeUnit.SECONDS)
    public void measureGet() throws Throwable {
        queue.poll();
    }
}
