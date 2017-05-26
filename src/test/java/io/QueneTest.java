package io;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.takin.emmet.io.traffic.shm.queue.Block;
import com.takin.emmet.io.traffic.shm.queue.Queue;
import com.takin.emmet.io.traffic.util.Util;

public class QueneTest {

    @Test
    public void read() {
        Queue queue = Queue.map("D:/shm", 2000L, 1, 0);
        queue.init();

        while (true) {
            try {
                Block block = queue.poll();
                if (block != null) {
                    System.out.println(new String(block.getPayload(), "UTF-8"));
                } else {
                    Util.pause(10);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void write() {
        try {
            Queue queue = Queue.map("D:/shm", 2000L, 1, 0);
            String string = "hello, world";
            byte[] bytes = string.getBytes("UTF-8");
            System.out.println(queue.put(new Block(bytes)));

            queue.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
