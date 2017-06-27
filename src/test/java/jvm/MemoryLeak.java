package jvm;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Maps;

public class MemoryLeak {

    private static final HashMap<String, String> map = Maps.newHashMap();

    private static int count = 0;

    public static void main(String[] args) {
        try {
            TimeUnit.SECONDS.sleep(10);
            System.out.println("start");
            while (true) {
                count++;
                map.put(count + "", count + "a");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
