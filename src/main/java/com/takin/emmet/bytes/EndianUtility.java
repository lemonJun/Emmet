package com.takin.emmet.bytes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class EndianUtility {

    /**
     * 2字节 bytes 转换为Short,Endian为小端
     * **/
    public static short bytesLittleToShort(byte[] data, int start) {
        short ret = 0;
        ret = (short) (data[start + 0] & 0x00ff);
        ret |= (0x00ff & data[start + 1]) << 8;
        return ret;
    }

    /**
     * 四字节 bytes 转换为Int,Endian为小端
     * **/
    public static int bytesLittleToInt(byte[] data, int start) {
        int ret = 0;
        ret = 0x000000ff & data[start + 0];
        ret |= (0x000000ff & data[start + 1]) << 8;
        ret |= (0x000000ff & data[start + 2]) << 16;
        ret |= (0x000000ff & data[start + 3]) << 24;
        return ret;
    }

    /**
     * 四字节 bytes 转换为Float,Endian为小端
     * **/
    public static float bytesLittleToFloat(byte[] data, int start) {
        int ret = 0;
        ret = 0x000000ff & data[start + 0];
        ret |= (0x000000ff & data[start + 1]) << 8;
        ret |= (0x000000ff & data[start + 2]) << 16;
        ret |= (0x000000ff & data[start + 3]) << 24;

        return Float.intBitsToFloat(ret);
    }

    /**
     * 8字节 bytes 转换为Long,Endian为小端
     * **/
    public static long bytesLittleToLong(byte[] data, int start) {
        long ret = 0;
        ret = 0x00000000000000ffL & data[start++];
        ret |= (0x00000000000000ffL & data[start++]) << 8;
        ret |= (0x00000000000000ffL & data[start++]) << 16;
        ret |= (0x00000000000000ffL & data[start++]) << 24;
        ret |= (0x00000000000000ffL & data[start++]) << 32;
        ret |= (0x00000000000000ffL & data[start++]) << 40;
        ret |= (0x00000000000000ffL & data[start++]) << 48;
        ret |= (0x00000000000000ffL & data[start++]) << 56;
        return ret;
    }

    /**
     * 8字节 bytes 转换为Double,Endian为小端
     * **/
    public static double bytesLittleToDouble(byte[] data, int start) {
        long ret = 0;
        ret = 0x00000000000000ffL & data[start++];
        ret |= (0x00000000000000ffL & data[start++]) << 8;
        ret |= (0x00000000000000ffL & data[start++]) << 16;
        ret |= (0x00000000000000ffL & data[start++]) << 24;
        ret |= (0x00000000000000ffL & data[start++]) << 32;
        ret |= (0x00000000000000ffL & data[start++]) << 40;
        ret |= (0x00000000000000ffL & data[start++]) << 48;
        ret |= (0x00000000000000ffL & data[start++]) << 56;
        return Double.longBitsToDouble(ret);
    }

    /**
     * 将一个四字节整数转化成 litter endian的整数.
     * @param n 要被转化的整数.
     * @return 转化后的整数.
     */
    public static int toLittleEndian(int n) {
        /*return (int) ((n & 0xff000000) >> 24 | 
        		(n & 0x00ff0000) >> 8 | 
        		(n & 0x0000ff00) << 8 | 
        		(n & 0x000000ff) << 24);*/

        int m = n & 0x000000ff;

        for (int i = 0; i < 3; i++) {
            m <<= 8;
            n >>= 8;
            m |= n & 0x000000ff;
        }

        return m;
    }

    /**
     * 将一个2字节整数转化成 litter endian的整数.
     * @param n 要被转化的整数.
     * @return 转化后的整数.
     */
    public static short toLittleEndian(short n) {
        return (short) ((n & 0xff00) >> 8 | (n & 0x00ff) << 8);
    }

    /**
     * 将一个八字节整数转化成 litter endian的整数.
     * @param n 要被转化的整数.
     * @return 转化后的整数.
     */
    public static long toLittleEndian(long n) {
        long m = n & 0x00000000000000ff;

        for (int i = 0; i < 7; i++) {
            m <<= 8;
            n >>= 8;
            m |= n & 0x00000000000000ff;
        }

        return m;
    }

    /**
     * 将一个四字节整数转化成 Big endian的整数.
     * @param n 要被转化的整数.
     * @return 转化后的整数.
     */
    public static int toBigEndian(int n) {
        return (int) ((n & 0xff000000) >> 24 | (n & 0x00ff0000) >> 8 | (n & 0x0000ff00) << 8 | (n & 0x000000ff) << 24);
    }

    /**
     * 将一个2字节整数转化成 Big endian的整数.
     * @param n 要被转化的整数.
     * @return 转化后的整数.
     */
    public static short toBigEndian(short n) {
        return (short) ((n & 0xff) >> 8 | (n & 0x00ff) << 8);
    }

    /**
     * 将一个八字节整数转化成 Big endian的整数.
     * @param n 要被转化的整数.
     * @return 转化后的整数.
     */
    public static long toBigEndian(long n) {
        long m = n & 0x00000000000000ff;

        for (int i = 0; i < 8; i++) {
            m <<= 8;
            n >>= 8;
            m |= n & 0x00000000000000ff;
        }

        return m;
    }

    public static void main(String[] args) {
        Integer value = 0x1234;
        System.out.print("0x1234 = ");
        for (int i = 0; i < 4; i++) {
            System.out.print("\t");
            System.out.print((value >> (i * 8)) & 0xFF);
        }
        System.out.print("\n");
        System.out.println("bit=0-31");
        for (int i = 0; i < 32; i++) {
            System.out.print((value >> i) & 0x01);
        }
        System.out.print("\n");

        System.out.println("value=" + value + ", toLittleEndian=" + toLittleEndian(value));

        System.out.println("当前系统order=" + ByteOrder.nativeOrder());

        ByteBuffer buffer = ByteBuffer.allocate(20);

        // 获取默认的byte顺序
        ByteOrder order = buffer.order(); // 
        System.out.println("当前order=" + order);

        buffer.putShort(0, (short) 1);
        buffer.get(0);
        System.out.println("此时取出0:" + buffer.get(0));
        buffer.get(1);
        System.out.println("此时取出1:" + buffer.get(1));

        buffer.order(ByteOrder.LITTLE_ENDIAN);
        System.out.println("当前order=" + buffer.order());

        buffer.putShort(0, (short) 1);
        buffer.get(0);
        System.out.println("此时取出1:" + buffer.get(0));
        buffer.get(1);
        System.out.println("此时取出0:" + buffer.get(1));

        //double float
        buffer.putDouble(0, 11.1111D);
        for (int i = 0; i < 8; i++) {
            System.out.print("\t" + buffer.get(i));
        }
        System.out.println();

        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.putDouble(0, 11.1111D);
        for (int i = 0; i < 8; i++) {
            System.out.print("\t" + buffer.get(i));
        }
        System.out.println();

        ByteBuffer buffer2 = ByteBuffer.allocate(20);
        buffer2.putLong(toLittleEndian(20140306192110L));
        buffer2.flip();
        byte[] data = buffer2.array();
        for (int i = 0; i < 8; ++i) {
            System.out.print(data[i] + ",");
        }
        System.out.println();
    }
}
