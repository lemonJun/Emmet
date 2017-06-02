package com.takin.emmet.bytes;

public class DataTypeUntil {

    public static int byteToInt(byte b) {
        //Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值  
        return b & 0xFF;
    }

    /**
     * 2字节 bytes 转换为Short,Endian为小端
     * **/
    public static short bytesToShort(byte[] data, int start) {
        short ret = 0;
        ret = (short) (data[start + 0] & 0x00ff);
        ret |= (0x00ff & data[start + 1]) << 8;
        return ret;
    }

    /**
     * 四字节 bytes 转换为Int,Endian为小端
     * **/
    public static int bytesToInt(byte[] data, int start) {
        int ret = 0;
        ret = 0x000000ff & data[start + 0];
        ret |= (0x000000ff & data[start + 1]) << 8;
        ret |= (0x000000ff & data[start + 2]) << 16;
        ret |= (0x000000ff & data[start + 3]) << 24;
        return ret;
    }

    /**
     * 8字节 bytes 转换为Long,Endian为小端
     * **/
    public static long bytesToLong(byte[] data, int start) {
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
     * 四字节 bytes 转换为Float,Endian为小端
     * **/
    public static float bytesToFloat(byte[] data, int start) {
        int ret = 0;
        ret = 0x000000ff & data[start + 0];
        ret |= (0x000000ff & data[start + 1]) << 8;
        ret |= (0x000000ff & data[start + 2]) << 16;
        ret |= (0x000000ff & data[start + 3]) << 24;

        return Float.intBitsToFloat(ret);
    }

    /**
     * 8字节 bytes 转换为Double,Endian为小端
     * **/
    public static double bytesToDouble(byte[] data, int start) {
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
}
