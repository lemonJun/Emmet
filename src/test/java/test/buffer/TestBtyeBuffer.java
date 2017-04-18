package test.buffer;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.junit.Test;

public class TestBtyeBuffer {

    //<fieldschema,list<posting>>

    @Test
    public void bytebuffer() {
        try {
            final ByteBuffer buf = ByteBuffer.allocate(20); //
            String token = "java";
            buf.put(token.getBytes("utf-8"));
            int positon = buf.position();
            String spi = "\001";
            buf.put(spi.getBytes("utf-8"));
            buf.putInt(11);//词频
            buf.put((byte) 2);//倒排个数
            buf.put((byte) 5);//字段的顺序
            buf.put((byte) 1);//位置
            buf.put((byte) 8);//字段的顺序
            buf.put((byte) 3);//位置

            buf.flip();
            byte[] bytes = new byte[positon];
            buf.get(bytes, 0, positon);
            System.out.println(new String(bytes));

            byte[] bytes2 = new byte[1];
            buf.get(bytes2, 0, 1);

            //            byte bytespi = buf.get();
            System.out.println(new String(bytes2));
            System.out.println(buf.getInt());
            System.out.println((int) buf.get());
            System.out.println((int) buf.get());
            System.out.println((int) buf.get());
            System.out.println((int) buf.get());
            System.out.println((int) buf.get());
            buf.clear();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
