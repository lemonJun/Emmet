package com.takin.emmet.bytes;

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

}
