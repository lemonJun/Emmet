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

package com.takin.emmet.io.traffic.util;

import java.lang.reflect.Method;
import java.util.concurrent.locks.LockSupport;

/**
 * @author cuiyi
 */
public abstract class Util {

    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();

    private static final boolean IS_WINDOWS = OS_NAME.startsWith("win");

    public static boolean isWindows() {
        return IS_WINDOWS;
    }

    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... params) {
        Assert.notNull(clazz, "Class must not be null");
        Assert.notNull(methodName, "Method name must not be null");

        try {
            Method method = clazz.getDeclaredMethod(methodName, params);
            method.setAccessible(true);
            return method;
        } catch (NoSuchMethodException ex) {
            throw new IllegalStateException("Expected method not found: " + ex);
        }
    }

    public static Object invokeMethod(Method method, Object target, Object... args) {
        try {
            return method.invoke(target, args);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static long align(long value, long alignment) {
        long mask = alignment - 1;
        return (value + mask) & ~mask; //((value + mask) / alignment) * alignment
    }

    public static void pause(long millis) {
        long timeNanos = millis * 1000000;
        if (timeNanos > 10e6) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else {
            LockSupport.parkNanos(timeNanos);
        }
    }

}
