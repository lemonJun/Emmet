package jvm;

import java.util.ArrayList;
import java.util.List;

public class AllocTest {

    public static void main(String[] args) {
        alloc();
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void alloc() {
        List<byte[]> list = new ArrayList<byte[]>();
        int size = 1024 * 1024 * 400;
        int len = size / (1024 * 5);
        for (int i = 0; i < len; i++) {
            byte[] bytes = new byte[20 * 1024];
            list.add(bytes);
        }
    }
}
